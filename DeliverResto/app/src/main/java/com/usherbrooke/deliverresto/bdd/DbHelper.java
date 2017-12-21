package com.usherbrooke.deliverresto.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;

import com.usherbrooke.deliverresto.R;
import com.usherbrooke.deliverresto.entities.ClientDao;
import com.usherbrooke.deliverresto.entities.MoyenPaiement;
import com.usherbrooke.deliverresto.services.AdresseService;
import com.usherbrooke.deliverresto.services.CategoriePlatService;
import com.usherbrooke.deliverresto.services.ClientService;
import com.usherbrooke.deliverresto.services.CommandeService;
import com.usherbrooke.deliverresto.services.IngredientService;
import com.usherbrooke.deliverresto.services.MagasinService;
import com.usherbrooke.deliverresto.services.MenuService;
import com.usherbrooke.deliverresto.services.MoyenPaiementService;
import com.usherbrooke.deliverresto.services.PlatService;
import com.usherbrooke.deliverresto.services.TypePlatService;
import com.usherbrooke.deliverresto.entities.Adresse;
import com.usherbrooke.deliverresto.entities.CategoriePlat;
import com.usherbrooke.deliverresto.entities.CategoriePlatDao;
import com.usherbrooke.deliverresto.entities.CategoriePlatStrings;
import com.usherbrooke.deliverresto.entities.Client;
import com.usherbrooke.deliverresto.entities.CommandeEtatStrings;
import com.usherbrooke.deliverresto.entities.DaoMaster;
import com.usherbrooke.deliverresto.entities.DaoSession;
import com.usherbrooke.deliverresto.entities.EtatCommande;
import com.usherbrooke.deliverresto.entities.EtatCommandeDao;
import com.usherbrooke.deliverresto.entities.Ingredient;
import com.usherbrooke.deliverresto.entities.IngredientStrings;
import com.usherbrooke.deliverresto.entities.Magasin;
import com.usherbrooke.deliverresto.entities.Plat;
import com.usherbrooke.deliverresto.entities.TypePlat;
import com.usherbrooke.deliverresto.entities.TypePlatDao;
import com.usherbrooke.deliverresto.entities.TypePlatStrings;

/**
 * Created by Valentin on 03/11/2017.
 */

public class DbHelper {
    private static DbHelper ourInstance = null;
    private static final String DB_NAME = "deliverresto-db";

    private DaoSession daoSession;

    private static AdresseService adresseService;
    private static ClientService clientService;
    private static CommandeService commandeService;
    private static IngredientService ingredientService;
    private static MenuService menuService;
    private static MoyenPaiementService moyenPaiementService;
    private static PlatService platService;
    private static TypePlatService typePlatService;
    private static CategoriePlatService categoriePlatService;
    private static MagasinService magasinService;

    private  Client connectedClient = null;
    private  Client subscribingClient = null;
    private Adresse subscribingAdresse = null;
    private MoyenPaiement subscribingMoyenPaiement = null;

    private DbHelper() {

    }

    private static DbHelper getInstance() {
        if (ourInstance == null)
            ourInstance = new DbHelper();
        return ourInstance;
    }

    public static void createDbHelper(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        getInstance().daoSession = daoMaster.newSession();

        adresseService = new AdresseService(getInstance().daoSession.getAdresseDao());
        clientService = new ClientService(getInstance().daoSession.getClientDao());
        commandeService = new CommandeService(getInstance().daoSession.getCommandeDao());
        ingredientService = new IngredientService(getInstance().daoSession.getIngredientDao());
        menuService = new MenuService(getInstance().daoSession.getMenuDao());
        moyenPaiementService = new MoyenPaiementService(getInstance().daoSession.getMoyenPaiementDao());
        platService = new PlatService(getInstance().daoSession.getPlatDao(), getInstance().daoSession.getTypePlatDao(), getInstance().daoSession.getCategoriePlatDao(), getIngredientService(), getInstance().daoSession.getPlatIngredientDao());
        typePlatService = new TypePlatService(getInstance().daoSession.getTypePlatDao());
        categoriePlatService = new CategoriePlatService(getInstance().daoSession.getCategoriePlatDao());
        magasinService = new MagasinService(getInstance().daoSession.getMagasinDao(), getInstance().daoSession.getAdresseDao());

//        insert() adds Entity into Table, assumes Entity with that Key does not exist. If exists, it shall throw Exception
//        insertOrReplace() adds Entity into Table if Key does not exist, replaces if Key exists.
//        save() adds Entity which has no Key into Table, updates if it has Key and Entity exists on Table. If Entity has Key and does not exist on Table, it will do nothing.

        deleteData();
        setupData();
    }

    public static Client getSubscribingClient() {
        return getInstance().subscribingClient;
    }

    public static void setSubscribingClient(Client subscribingClient) {
        getInstance().subscribingClient = subscribingClient;
    }

    public static Adresse getSubscribingAdresse() {
        return  getInstance().subscribingAdresse;
    }

    public static void setSubscribingAdresse(Adresse subscribingAdresse) {
        getInstance().subscribingAdresse = subscribingAdresse;
    }

    public static MoyenPaiement getSubscribingMoyenPaiement() {
        return  getInstance().subscribingMoyenPaiement;
    }

    public static void setSubscribingMoyenPaiement(MoyenPaiement subscribingMoyenPaiement) {
        getInstance().subscribingMoyenPaiement = subscribingMoyenPaiement;
    }

    public static void saveSubscribingClient(){
        if(getInstance().subscribingAdresse != null){
            DbHelper.getAdresseService().save(DbHelper.getSubscribingAdresse());
            Adresse adr = DbHelper.getAdresseService().getAdresseByRueVille(getInstance().subscribingAdresse.getRue(), getInstance().subscribingAdresse.getVille());
            getInstance().subscribingClient.setAdresse(adr);
            getInstance().subscribingClient.setAdresseId(adr.getId());
        }

        if(getInstance().subscribingMoyenPaiement != null){
            DbHelper.getMoyenPaiementService().save(DbHelper.getSubscribingMoyenPaiement());
            MoyenPaiement mp = DbHelper.getMoyenPaiementService().getMoyenPaiementByNumCarte(getInstance().subscribingMoyenPaiement.getNumCB());
            getInstance().subscribingClient.setMoyenPaiement(mp);
            getInstance().subscribingClient.setMoyenPaiementId(mp.getId());
        }

        DbHelper.getClientService().save(DbHelper.getSubscribingClient());
    }

    public static void resetSubscribingClient(){
        getInstance().subscribingClient = null;
        getInstance().subscribingAdresse = null;
        getInstance().subscribingMoyenPaiement = null;
    }

    public static Client getConnectedClient() {
        return getInstance().connectedClient;
    }

    public static void deconnectClient(){
        getInstance().connectedClient = null;
    }

    public static void setConnectedClient(Client connectedClient) {
        getInstance().connectedClient = connectedClient;
    }

    private static void setupData(){
        //create enum
        createTypesPlat(TypePlatStrings.ENTREE, TypePlatStrings.PLAT, TypePlatStrings.DESSERT, TypePlatStrings.BOISSON, TypePlatStrings.MENU);
        createCategoriesPlat(CategoriePlatStrings.SALADE, CategoriePlatStrings.SOUPE, CategoriePlatStrings.BURGER, CategoriePlatStrings.PIZZA, CategoriePlatStrings.DESSERT, CategoriePlatStrings.BIERE, CategoriePlatStrings.SODA);
        createEtatsCommande(CommandeEtatStrings.A_PREPARER, CommandeEtatStrings.PRETE, CommandeEtatStrings.EN_LIVRAISON_NON_PAYEE, CommandeEtatStrings.EN_LIVRAISON_PAYEE, CommandeEtatStrings.TERMINEE);//todo
        createMagasins();

        createClient();

        createIngredients();
        createPlats();
    }

    public static void deleteData(){
        getInstance().daoSession.getPlatIngredientDao().deleteAll();
        getInstance().daoSession.getCommandePlatDao().deleteAll();

        getInstance().daoSession.getTypePlatDao().deleteAll();
        getInstance().daoSession.getCategoriePlatDao().deleteAll();

        getInstance().daoSession.getClientDao().deleteAll();

        getInstance().daoSession.getCommandeDao().deleteAll();
        getInstance().daoSession.getMagasinDao().deleteAll();
        getInstance().daoSession.getEtatCommandeDao().deleteAll();

        getInstance().daoSession.getAdresseDao().deleteAll();
        getInstance().daoSession.getMoyenPaiementDao().deleteAll();

        getInstance().daoSession.getPlatDao().deleteAll();
        getInstance().daoSession.getIngredientDao().deleteAll();
        getInstance().daoSession.getTypePlatDao().deleteAll();
        getInstance().daoSession.getCategoriePlatDao().deleteAll();


    }

    public static void createClient(){
        Client c = new Client(null, "Jean", "Michel");
        c.setCourriel("aaa@aaa.fr");
        c.setMdp(Base64.encode("azerty".getBytes(), Base64.DEFAULT));
        getInstance().daoSession.getClientDao().save(c);
    }

    public static void createMagasins(){
        createMagasin("Magasin de Sherbrooke", "7 rue Fontenac", "J1J 2V2", "Sherbrooke", "QC", "");
        createMagasin("Magasin de Windsor","15 Rue Principale N", "J1S 2C3", "Windsor", "QC", "" );
        createMagasin("Magasin de Magog", "204 Rue Saint-Patrice Ouest", "J1X 1W3", "Magog", "QC", "");
    }

    private static void createMagasin(String nom, String rue, String codePostal, String ville, String province, String indication){
        Adresse a = new Adresse(null, rue, codePostal, ville, province, indication);
        getInstance().daoSession.getAdresseDao().save(a);

        a = getAdresseService().getAdresseByRueVille(rue, ville);
        Magasin m = new Magasin(null, nom, a.getId());
        m.setAdresse(a);
        getInstance().daoSession.getMagasinDao().save(m);
    }

    private static void createPlats() {
        createPlat("Maori", 7.5f, TypePlatStrings.ENTREE, CategoriePlatStrings.SALADE, R.drawable.entrees_background,
                IngredientStrings.LAITUE, IngredientStrings.OIGNONS, IngredientStrings.CAROTTES);
        createPlat("Marocaine", 8.5f, TypePlatStrings.ENTREE, CategoriePlatStrings.SALADE, R.drawable.entrees_background,
                IngredientStrings.RIZ, IngredientStrings.THON, IngredientStrings.POIVRON_ROUGE);
        createPlat("Legumes", 6.5f, TypePlatStrings.ENTREE, CategoriePlatStrings.SOUPE, R.drawable.entrees_background,
                IngredientStrings.CAROTTES, IngredientStrings.TOMATES, IngredientStrings.COURGETTES);

        createPlat("Cerf", 12.5f, TypePlatStrings.PLAT, CategoriePlatStrings.BURGER, R.drawable.entrees_background,
                IngredientStrings.CERF, IngredientStrings.TOMATES, IngredientStrings.LAITUE);
        createPlat("James Bun", 10.5f, TypePlatStrings.PLAT, CategoriePlatStrings.BURGER, R.drawable.entrees_background,
                IngredientStrings.POULET, IngredientStrings.LAITUE, IngredientStrings.FRITES);
        createPlat("Cannibale", 9.5f, TypePlatStrings.PLAT, CategoriePlatStrings.PIZZA, R.drawable.entrees_background,
                IngredientStrings.SAUCE_BBQ, IngredientStrings.CERF, IngredientStrings.POULET);

        createPlat("Brownie", 6.5f, TypePlatStrings.DESSERT, CategoriePlatStrings.DESSERT, R.drawable.entrees_background,
                IngredientStrings.CHOCOLAT);
        createPlat("Fondant", 6.5f, TypePlatStrings.DESSERT, CategoriePlatStrings.DESSERT, R.drawable.entrees_background,
                IngredientStrings.CHOCOLAT);

        createPlat("Griffon Rousse", 6.5f, TypePlatStrings.BOISSON, CategoriePlatStrings.BIERE, R.drawable.entrees_background);
        createPlat("Thé Glacé", 4.5f, TypePlatStrings.BOISSON, CategoriePlatStrings.SODA, R.drawable.entrees_background);
        createPlat("Jus Tropical", 5f, TypePlatStrings.BOISSON, CategoriePlatStrings.SODA, R.drawable.entrees_background);
    }

    public static void createPlat(String nom, float prix, String typePlat, String categoriePlat, int drawable, String... ingredients) {
        TypePlat tp = typePlatService.getTypePlatByName(typePlat);
        CategoriePlat cp = categoriePlatService.getCategoriePlatByName(categoriePlat);
        Plat p = new Plat(null, nom, prix, tp.getId(), cp.getId(), drawable);
        getInstance().daoSession.getPlatDao().save(p);

        platService.addIngredientsToPlat(platService.getPlatByName(nom), ingredients);
    }

    public static void createIngredients() {
        getInstance().daoSession.getIngredientDao().insertOrReplaceInTx(
                new Ingredient(null, IngredientStrings.LAITUE),
                new Ingredient(null, IngredientStrings.OIGNONS),
                new Ingredient(null, IngredientStrings.CAROTTES),
                new Ingredient(null, IngredientStrings.TOMATES),
                new Ingredient(null, IngredientStrings.RADIS),
                new Ingredient(null, IngredientStrings.RIZ),
                new Ingredient(null, IngredientStrings.THON),
                new Ingredient(null, IngredientStrings.CONCOMBRE),
                new Ingredient(null, IngredientStrings.HARICOTS_VERTS),
                new Ingredient(null, IngredientStrings.COURGETTES),
                new Ingredient(null, IngredientStrings.CHAMPIGNONS),
                new Ingredient(null, IngredientStrings.AMANDES),
                new Ingredient(null, IngredientStrings.POIVRON_ROUGE),
                new Ingredient(null, IngredientStrings.OEUF),
                new Ingredient(null, IngredientStrings.CERF),
                new Ingredient(null, IngredientStrings.POULET),
                new Ingredient(null, IngredientStrings.FRITES),
                new Ingredient(null, IngredientStrings.SAUCE_BBQ),
                new Ingredient(null, IngredientStrings.CHOCOLAT)
        );
    }


    public static void createTypesPlat(String... type) {
        TypePlatDao typePlatDao = getInstance().daoSession.getTypePlatDao();

        for (int i = 0; i < type.length; i++) {
            typePlatDao.insertOrReplace(new TypePlat(null, type[i]));
        }
    }

    public static void createCategoriesPlat(String... cat) {
        CategoriePlatDao categoriePlatDao = getInstance().daoSession.getCategoriePlatDao();

        for (int i = 0; i < cat.length; i++) {
            categoriePlatDao.save(new CategoriePlat(null, cat[i]));
        }
    }

    public static void createEtatsCommande(String... etat) {
        EtatCommandeDao etatCommandeDapo = getInstance().daoSession.getEtatCommandeDao();

        for (int i = 0; i < etat.length; i++) {
            etatCommandeDapo.insertOrReplace(new EtatCommande(null, etat[i]));
        }
    }

    public static MagasinService getMagasinService() {
        return magasinService;
    }

    public static TypePlatService getTypePlatService() {
        return typePlatService;
    }

    public static CategoriePlatService getCategoriePlatService() {
        return categoriePlatService;
    }

    public static AdresseService getAdresseService() {
        return adresseService;
    }

    public static ClientService getClientService() {
        return clientService;
    }

    public static CommandeService getCommandeService() {
        return commandeService;
    }

    public static IngredientService getIngredientService() {
        return ingredientService;
    }

    public static MenuService getMenuService() {
        return menuService;
    }

    public static MoyenPaiementService getMoyenPaiementService() {
        return moyenPaiementService;
    }

    public static PlatService getPlatService() {
        return platService;
    }
}
