package Project;
import Project.Classes.*;
import Project.Classes.Comparator.ProductComparator;
import Project.Classes.Product;
import Project.Classes.Users.*;
//import groovyjarjarantlr4.v4.runtime.dfa.HashEdgeMap;
import javafx.util.Pair;
//import jdk.jshell.spi.ExecutionControl;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Services {
    private static Services instance;
    private static final Scanner read = new Scanner(System.in);
    private ArrayList<Product> productsList = new ArrayList<Product>();
    private Map<UUID,Product>productsMap = new HashMap<>();
    private Map<UUID,Costumer> costumersList = new HashMap<>();
    private ArrayList<Adress> adressesList = new ArrayList<Adress>();
    private Map<UUID,Store> storesList = new HashMap<>();
    private Map<UUID,Order> orderList = new HashMap<>();
    private Map<UUID,Voucher> voucherMap = new HashMap<>();
    private  ArrayList<Pair<Store,ArrayList<Product>>> storeWithProductsList = new ArrayList<Pair<Store,ArrayList<Product>>>();
    private Map<Product,ArrayList<Review>> productWithReviewas = new HashMap<Product,ArrayList<Review>>();

    private Services() {
           init();
           menu();

    }

     public static Services createServices() {
        if (instance == null) {
            instance = new Services();
        }
        return instance;
    }


    private void init(){
        Adress a1 = new Adress("Brasov","Sforii",1,"Brasov");
        Adress a2=  new Adress("Brasov","Toamnei",5,"Brasov");
        Adress a3 = new Adress("Sibiu","Buhusi",2,"Sibiu");
        Adress a4 = new Adress("Cluj","Mica",31, "Cluj");

        adressesList.add(0,a1);
        adressesList.add(0,a2);
        adressesList.add(0,a3);
        adressesList.add(0,a4);

        Pair<Integer,Integer> p1 = new Pair<>(8,19);
        Pair<Integer,Integer> p2 = new Pair<>(9,15);
        Pair<Integer,Integer> p3 = new Pair<>(9,20);
        Pair<Integer,Integer> p4 = new Pair<>(10,14);

        ArrayList<Pair<Integer, Integer>> program1 = new ArrayList<>(List.of(p1,p1,p1,p1,p1,p2,p2));
        ArrayList<Pair<Integer, Integer>> program2 = new ArrayList<>(List.of(p3,p3,p3,p3,p3,p4,p4));
        ArrayList<Pair<Integer, Integer>> program3 = new ArrayList<>(List.of(p1,p1,p1,p1,p1,p2,p2));
        ArrayList<Pair<Integer, Integer>> program4 = new ArrayList<>(List.of(p3,p3,p3,p3,p3,p4,p4));

        Instrument i1 = new Instrument("saxophone",null,2345.67,2019,3,null,"music",null);
        Instrument i2 = new Instrument("Violin",null,3455.67,2019,5,null,"music",null);
        Instrument i3 = new Instrument("trumpet",null,1155.67,2011,2,null,"music",null);
        Instrument i4 = new Instrument("trombone",null,2455.87,2009,5,null,"music",null);

        Accessory acc1 = new Accessory("microphone",null,1000,null,"studio",false,null);
        Accessory acc2 = new Accessory("headphones",null,800,null,"studio",false,null);
        Accessory acc3 = new Accessory("headphones",null,800,null,"studio",false,null);
        Accessory acc4 = new Accessory("audio pc cuble",null,100,null,"home audio",false,null);

        productsMap.put(i1.getId(),i1);
        productsMap.put(i2.getId(),i2);
        productsMap.put(i3.getId(),i3);
        productsMap.put(i4.getId(),i4);
        productsMap.put(acc1.getId(),acc1);
        productsMap.put(acc2.getId(),acc2);
        productsMap.put(acc3.getId(),acc3);
        productsMap.put(acc4.getId(),acc4);

        ArrayList<Product> products1 = new ArrayList<>(Arrays.asList(i1,i2,i3,acc1,acc2));
        ArrayList<Product> products2 = new ArrayList<>(Arrays.asList(i1,i2,i4,acc3,acc4));
        ArrayList<Product> products3 = new ArrayList<>(Arrays.asList(i4,i2,i1,acc1,acc3));
        ArrayList<Product> products4 = new ArrayList<>(Arrays.asList(i1,i2,acc3,acc1,acc2));

        Store s1 = new Store("Your music",a1,products1.size(),"open",program1,products1);
        Store s2 = new Store("Music",a3,products2.size(),"open",program2,products2);
        Store s3 = new Store("Perfect Music Store",a2,products4.size(),"open",program4,products4);
        Store s4 = new Store("Music",a4,products3.size(),"open",program3,products3);

        storesList.put(s1.getId(),s1);
        storesList.put(s2.getId(),s2);
        storesList.put(s3.getId(),s3);
        storesList.put(s4.getId(),s4);

        Costumer c1 = new Costumer("Popescu","Ionut",null,null);
        Costumer c2 = new Costumer("Marinescu","Maria",null,null);
        Costumer c3 = new Costumer("Ionescu","Ionela",null,null);
        Costumer c4 = new Costumer("Mihai","Ionut",null,null);
        Costumer c5 = new Costumer("Popa","Elena",null,null);

        costumersList.put(c1.getId(),c1);
        costumersList.put(c2.getId(),c2);
        costumersList.put(c3.getId(),c3);
        costumersList.put(c4.getId(),c4);
        costumersList.put(c5.getId(),c5);

        addAStoreInListWithProducts();
    }


    private void checkValidInput(int value) throws IOException {
        if (value >= 3 || value<1) {
            throw new IOException("Invalid input");
        }
    }

    private void checkValidAdmin(boolean value) throws IOException {
        if (!value) {
            throw new IOException("Invalid id or password!");
        }
    }

    private boolean login(){
        System.out.println("Please, connect!");
        System.out.println("Id: ");
        String id = read.nextLine();
         
        if(id.equals(Admin.createAdmin().getId())) {
            System.out.println("Password: ");
            String password = read.nextLine();
            return password.equals(Admin.createAdmin().getPassword());
        }
        return false;
    }

    private void checkConnection(){
       boolean check = false;
        while(!check){
            try {
                checkValidAdmin(login());
                check = true;
            } catch (IOException  ime) {
                System.out.println("Invalid id or password! Please connect again!");
            }
        }
        System.out.println("Login successful!");
    }

    private void adminMenu(){
        boolean continueMenu = true;
        while(continueMenu){
            System.out.println("\n\tWelcome!\t\n \tYou are admin\t");
            System.out.println("1. Create a store and add this in list (Plus, add each adress in list).");
            System.out.println("2. Create a product and add this in list.");
            System.out.println("3. Create a costumer and add this in list.");
            System.out.println("4. Register a order into a store and add this in list.");
            System.out.println("5. Order each store products according to decreasing order by the price of each product.");
            System.out.println("6. Register a voucher in vouchers list.");
            System.out.println("7. Exit.");

            System.out.println("Please, enter an option: ");
            int choice = read.nextInt();
            read.nextLine();
            switch(choice){
                case 1:
                    Store store = createAStore();
                    addAdressInList(store.getAdress());
                    addStoreInList(store);
                    break;
                case 2:
                    Product product = createAProduct();
                    addProductInList(product);
                    break;
                case 3:
                    Costumer costumer = createCostumer();
                    addCostumerInMap(costumer);
                    break;
                case 4:
                    Order order = createOrderAdmin();
                    addOrderInMap(order);
                    break;
                case 5:
                    orderStories();
                    break;
                case 6:
                    Voucher voucher = createVoucher();
                    addVoucherInMap(voucher);
                    break;
                case 7:
                    continueMenu = false;
                    break;
            }
        }
    }

    private void costumerMenu(){
        boolean continueMenu = true;
        while(continueMenu){
            System.out.println("\n\tWelcome!\t\n \tYou are costumer\t");
            System.out.println("1. View all products from all stores with adresses.");
            System.out.println("2. Order each store products according to decreasing order by the price of each product. ");
            System.out.println("3. Create an review for a product and add to list.");
            System.out.println("4. Exit.");

            System.out.println("Please, enter an option: ");
            int choice = read.nextInt();
            read.nextLine();
            switch(choice){
                case 1:
                    viewAllProducts();
                    break;
                case 2:
                    orderStories();
                    break;
                case 3:
                    for(Map.Entry<UUID,Product> pair : productsMap.entrySet()){
                        System.out.println(pair.getValue().getId() + " " + pair.getValue().getName());
                    }

                    System.out.println("Please, enter the id product: ");
                    String idProductString = read.nextLine();
                    UUID idProduct = UUID.fromString(idProductString);
                    Product product = null;
                    for(Map.Entry<UUID,Product> pair : productsMap.entrySet()){
                        if(idProduct.equals(pair.getValue().getId())) {
                            product  = pair.getValue();
                        }
                    }
                    Review review = createReview();
                    addReviewsMap(product,review);
                case 4:
                    continueMenu = false;
                    break;
            }
        }

    }

    private int choose1Or2(){
        boolean check = false;
        int choice=0;
        while (!check) {
            try {
                choice = read.nextInt();
                read.nextLine();
                checkValidInput(choice);
                check = true;
            } catch (InputMismatchException ime) {
                System.out.println("Please enter a valid value. (1 or 2)");
                read.nextLine();
            } catch( IOException ioe){
                System.out.println("Please enter one number between 1 and 2.");
            }
        }
        return choice;
    }

    public void menu(){
          System.out.println("\tAlegeti 1 sau 2.\t");
          System.out.println("1. Admin?");
          System.out.println("2. Costumer?");
          System.out.println("Your choice is: ");
          int choice = choose1Or2();

          if(choice==1) {
              checkConnection();
              adminMenu();
          }else {
              costumerMenu();
          }
    }

    private Voucher createVoucher(){
        System.out.println("Please, enter voucher type: ");
        String voucherType = read.nextLine();
        System.out.println("Please, enter mesaje: ");
        String mesaje = read.nextLine();
        System.out.println("Please, enter voucher value: ");
        double value =  read.nextDouble();
        read.nextLine();

        return new Voucher(voucherType,mesaje,value);
    }

    private Adress createAnAdress(){
        System.out.println("Please, enter a city: ");
        String city = read.nextLine();
        System.out.println("Please, enter a street: ");
        String street = read.nextLine();
        System.out.println("Please, enter street number: ");
        int number = read.nextInt();
        read.nextLine();
        System.out.println("Please, enter county city: ");
        String county = read.nextLine();

        return new Adress(city,street,number,county);
    }

    private void addAdressInList(Adress adress){
       adressesList.add(adress);
    }

    private Card createACard(){
        System.out.println("Please, enter card number: ");
        long  number = read.nextLong();
        read.nextLine();

        String next = read.next("[0-9]{2}.[0-9]{2}.[0-9]{4}");
        System.out.println("Please, enter card valid thru date (mm/dd/yyyy): ");
        Date validThru = null;
        try {
             validThru = new SimpleDateFormat("dd.MM.yyyy").parse(next);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Please, enter card type: ");
        String type = read.nextLine();
        System.out.println("Please, enter authorised signature: ");
        String authorisedSignatureHelp = read.next("[0-9]{6}");
        int authorisedSignature = Integer.parseInt(authorisedSignatureHelp);
        return new Card(number,validThru,type,authorisedSignature);
    }

    private Costumer createCostumer(){
        System.out.println("Please, enter the firstname of costumer: ");
        String firstName = read.nextLine();
        System.out.println("Please, enter the lastname of costumer: ");
        String lastName = read.nextLine();
        System.out.println("Do you want to add your adress? (Y/N)");
        Adress adress = null;
        Card card = null;
        char choiceHere = checkYesOrNo();
        if(choiceHere=='Y' || choiceHere == 'y')
            adress = createAnAdress();
        System.out.println("Do you want to add your card? (Y/N)");
        choiceHere = checkYesOrNo();
        if(choiceHere=='Y' || choiceHere == 'y')
            card = createACard();
        return new Costumer(firstName,lastName,adress,card);
    }

    private Order createOrderAdmin(){
        System.out.println("Do you want to add a new costumer? (Y/N)");
        Costumer costumer = null;
        char choiceHere = checkYesOrNo();
        if(choiceHere=='Y' || choiceHere == 'y')
        {
            costumer = createCostumer();
        }
        System.out.println("Please, enter payment method (Card/Cash): ");
        String paymentMethod = read.nextLine();
        System.out.println("\tPlease, enter the id store:\t");
        String idStore = read.nextLine();
        UUID id = UUID.fromString(idStore);
        Store thisStore = null;
        for(Map.Entry<UUID,Store> pair : storesList.entrySet()){
            if(id.equals(pair.getKey())){
                 thisStore = new Store(pair.getValue());
            }
        }
//        System.out.println("Please, enter the price product: ");
//        double price = read.nextDouble();

        System.out.println("Please, enter the number of products which you want to buy: ");
        int numberProducts = read.nextInt();
        read.nextLine();

        ArrayList<Product> products = new ArrayList<>(numberProducts);
        ArrayList<UUID> idList  = new ArrayList<>(numberProducts);
        for(int i=0;i<numberProducts;i++){
            System.out.println("Please, enter the id product: ");
            String idProductString = read.nextLine();
            UUID idProduct =  UUID.fromString(idProductString);
            idList.add(i,idProduct);
        }
        double price = 0;
        for(int index = 0;index<numberProducts;index++)
        {
            UUID currentId = idList.get(index);
            for(Map.Entry<UUID,Product> pair : productsMap.entrySet()){
                if(currentId.equals(pair.getKey())){
                    products.add(pair.getValue());
                    price+=pair.getValue().getPrice();
                }
            }
        }


        return new Order(costumer,paymentMethod,thisStore,price,products);
    }

    private Review createReview(){
        System.out.println("Please, enter your id:");
        String idCostumerString = read.nextLine();
        UUID idCostumer = UUID.fromString(idCostumerString);
        Costumer costumer = null;
        for(Map.Entry<UUID,Costumer> pair : costumersList.entrySet()){
            if(idCostumer.equals(pair.getKey())){
                costumer = pair.getValue();
            }
        }
        System.out.println("Please, enter a mesaje for this product.");
        String mesaje = read.nextLine();
        System.out.println("Please, add a note for this product (1->10)");
        int numberStars = read.nextInt();
        read.nextLine();
        return new Review(costumer,mesaje,numberStars);
    }

    private Product createAProduct(){
        System.out.println("\tWhat kind of product would you like to create?\t ");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("1. Add an instrument.");
        System.out.println("2. Add an accessory.");
        int choice = choose1Or2();
        if(choice==1){
            System.out.println("Please, enter the name of product: ");
            String name = read.nextLine();
            Review review = null;
            System.out.println("Please, enter price product: ");
            int price = read.nextInt();
            read.nextLine();
            System.out.println("Please, enter the year of production: ");
            int yearCreated = read.nextInt();
            read.nextLine();
            System.out.println("Please, enter the period warranty(years)");
            int warranty = read.nextInt();
            read.nextLine();
            System.out.println("Please, enter the manufacturer: ");
            String manufacturer = read.nextLine();
            System.out.println("Please, enter the category: ");
            String category = read.nextLine();
            System.out.println("Please, enter a description: ");
            String description = read.nextLine();
            return new Instrument(name,review,price,yearCreated,warranty,manufacturer,category,description);

        }else{
            System.out.println("Please, enter the name of product: ");
            String name = read.nextLine();
            Review review = null;
            System.out.println("Please, enter price product: ");
            int price = read.nextInt();
            read.nextLine();
            System.out.println("Please, enter the manufacturer: ");
            String manufacturer = read.nextLine();
            System.out.println("Please, enter the category: ");
            String category = read.nextLine();
            System.out.println("Please, enter a description: ");

            boolean gift = false;
            System.out.println("Please, choose if this accessory is a gift or no.(Y/N)");
            char choiceHere = checkYesOrNo();
            if(choiceHere=='Y' || choiceHere == 'y')
                gift = true;
            System.out.println("Please, enter a description: ");
            String description = read.nextLine();

            return new Accessory(name,review,price,manufacturer,category,gift,description);
        }
    }

    private void addProductInList(Product product){
        productsList.add(product);
    }

    private void yesOrNo(char value) throws IOException{
        if(value!='Y' && value!='N' && value!='y' && value!='n')
            throw new IOException("Invalid input!");
    }

    private char checkYesOrNo(){
        boolean check = false;
        char choice = ' ';
        while (!check) {
            try {
                choice = (char) System.in.read();
                yesOrNo(choice);
                check = true;
            } catch( IOException ioe){
                System.out.println("Please enter Y/y or N/n");
            }
        }
        return choice;
    }

    private  ArrayList<Pair<Integer, Integer>> addProgram(){
        ArrayList<Pair<Integer, Integer>> program = new ArrayList<Pair<Integer, Integer>>(7);
        program.add(0,null);
        for(int day = 0;day<7;day++){
            int openAt = read.nextInt();
            read.nextLine();
            int closingAt = read.nextInt();
            read.nextLine();
            Pair<Integer,Integer> interval = new Pair<Integer, Integer>(openAt,closingAt);
            program.add(day,interval);
        }
        return program;
    }

    private Store createAStore(){
        System.out.println("Please, enter the name of the store: ");
        String name = read.nextLine();
        Adress adress = createAnAdress();
        System.out.println("Please, enter the status of the store (open/closed):");
        String status = read.nextLine();
        int numberProducts = 0;
        ArrayList<Pair<Integer, Integer>> program = null;
        ArrayList<Product> productsListForThis = new ArrayList<Product>();
        System.out.println("Do you want to add a list of products? (Y/N)");
        char choice = checkYesOrNo();

        if(choice == 'Y' || choice =='y')
        {
            System.out.println("How many products would you like to add? ");

            boolean check = false;
            while(!check)
            {
                try{
                numberProducts = read.nextInt();
                read.nextLine();
                checkValidInput(numberProducts);
                check = true;
                }catch (IOException | InputMismatchException ioe){
                    System.out.println("Please enter one valid number!");
                }
            }
            for(int i=1;i<=numberProducts;i++) {
                addProductInList(createAProduct()); ///nu am terminat
            }
        }
        System.out.println("Do you want to add a program for each day of the week?(Y/N)");
        choice = checkYesOrNo();
        if(choice=='Y' || choice=='y'){
            program = addProgram();
        }
        return new Store(name,adress,numberProducts,status,program,productsList);
    }

    private void addStoreInList(Store store){
        UUID id = store.getId();
        storesList.put(id,store);
    }

    private void addAStoreInListWithProducts(){
        for(Map.Entry<UUID,Store> pair : storesList.entrySet()){
           Pair<Store,ArrayList<Product>>pairStoreProductsList = new Pair<>(pair.getValue(),pair.getValue().getProducts());
            storeWithProductsList.add(pairStoreProductsList);
        }
    }

    private void addCostumerInMap(Costumer costumer){
        UUID id = costumer.getId();
        costumersList.put(id,costumer);
    }

    private void addOrderInMap(Order order){
        UUID id = order.getId();
        orderList.put(id,order);
    }

    private void addProductInMap(Product product){
        UUID id = product.getId();
        productsMap.put(id,product);
    }


    private void orderStories(){
        for(Map.Entry<UUID,Store> pair : storesList.entrySet()){
            ArrayList<Product> products = pair.getValue().getProducts();
            products.sort(new ProductComparator());
            pair.getValue().setProducts(products);
//            storesList.replace(pair.getKey(),pair.getValue());
        }
        printSortResult();
    }

    private void printSortResult(){
        for(Map.Entry<UUID,Store> pair : storesList.entrySet()){
            System.out.println(pair.getValue());
        }
    }

    private void addVoucherInMap(Voucher voucher){
        voucherMap.put(voucher.getId(),voucher);
    }

    private void viewAllProducts(){
        for(Map.Entry<UUID,Store> pair : storesList.entrySet()){
            System.out.println(pair.getValue().getProducts() + " " + pair.getValue().getAdress());
        }
    }

    private void addReviewsMap(Product product,Review review){
        productWithReviewas.get(product).add(review);
    }
}
