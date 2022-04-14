package com.company.services;

import com.company.stores.orders.Card;
import com.company.stores.orders.Order;
import com.company.stores.products.Accessory;
import com.company.stores.products.Instrument;
import com.company.stores.products.Product;
import com.company.stores.products.Review;
import com.company.users.Admin;
import com.company.users.Customer;
import com.company.comparator.ProductComparator;
import com.company.stores.Address;
import com.company.stores.Store;
import com.company.stores.Voucher;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Services {
    private static Services instance;
    private static final Scanner read = new Scanner(System.in);
    private ArrayList<Product> productsList = new ArrayList<Product>();
    private Map<UUID,Product>productsMap = new HashMap<>();
    private Map<UUID, Customer> costumersList = new HashMap<>();
    private ArrayList<Address> adressesList = new ArrayList<Address>();
    private Map<UUID, Store> storesList = new HashMap<>();
    private Map<UUID, Order> orderList = new HashMap<>();
    private Map<UUID, Voucher> voucherMap = new HashMap<>();
    private ArrayList<Map<Store, ArrayList<Product>>> storeWithProductsList = new ArrayList<>();
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
        Address a1 = new Address("Brasov","Sforii",1,"Brasov");
        Address a2=  new Address("Brasov","Toamnei",5,"Brasov");
        Address a3 = new Address("Sibiu","Buhusi",2,"Sibiu");
        Address a4 = new Address("Cluj","Mica",31, "Cluj");

        adressesList.add(0,a1);
        adressesList.add(0,a2);
        adressesList.add(0,a3);
        adressesList.add(0,a4);

        Map<Integer,Integer>p1 =  new HashMap<>();
        Map<Integer,Integer>p2 =  new HashMap<>();
        Map<Integer,Integer>p3 =  new HashMap<>();
        Map<Integer,Integer>p4 =  new HashMap<>();

        p1.put(8,19);
        p2.put(9,15);
        p3.put(9,20);
        p4.put(10,14);

        ArrayList<Map<Integer, Integer>> program1 = new ArrayList<>(List.of(p1,p1,p1,p1,p1,p2,p2));
        ArrayList<Map<Integer, Integer>> program2 = new ArrayList<>(List.of(p3,p3,p3,p3,p3,p4,p4));
        ArrayList<Map<Integer, Integer>> program3 = new ArrayList<>(List.of(p1,p1,p1,p1,p1,p2,p2));
        ArrayList<Map<Integer, Integer>> program4 = new ArrayList<>(List.of(p3,p3,p3,p3,p3,p4,p4));


        Instrument i1 = new Instrument("saxophone",null,2345.67,null,"music",null,2019,3);
        Instrument i2 = new Instrument("Violin",null,3455.67,null,"music",null,2017,3);
        Instrument i3 = new Instrument("trumpet", null, 1155.67, null, "music", null, 2019, 2 );
        Instrument i4 = new Instrument("trombone", null, 2455.87, null, "music", null, 2009, 7 );
        Instrument test = new Instrument("bla", null, 22.4, "manufacturer", "category", "description", 2019, 2 );

        Accessory acc1 = new Accessory("microphone",null,1000,null,"studio",null,false);
        Accessory acc2 = new Accessory("headphones",null,800,null,"studio",null,false);
        Accessory acc3 = new Accessory("headphones",null,800,null,"studio",null,true);
        Accessory acc4 = new Accessory("audio pc cuble",null,100,null,"home audio",null,false);

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

        Customer c1 = new Customer("Popescu","Ionut",null,null);
        Customer c2 = new Customer("Marinescu","Maria",null,null);
        Customer c3 = new Customer("Ionescu","Ionela",null,null);
        Customer c4 = new Customer("Mihai","Ionut",null,null);
        Customer c5 = new Customer("Popa","Elena",null,null);

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

    private Product checkIfIdExistInProducts(UUID id) throws IOException{
        Product product = null;
        boolean check = false;
        for(Map.Entry<UUID,Product> pair : productsMap.entrySet()) {
            if(id.equals(pair.getKey())){
                check = true;
                product = pair.getValue();
            }
        }
        if(!check){
            throw new IOException("Id store doesn't exist!");
        }
        return product;
    }

    private void checkIfIdExistInStores(UUID id) throws IOException{
        boolean check = false;
        for(Map<Store, ArrayList<Product>> pairMap : storeWithProductsList) {
            for(Map.Entry<Store, ArrayList<Product>> pair: pairMap.entrySet()){
                if(id.equals(pair.getKey().getId())){
                    check = true;
                    System.out.println("\n" + pair.getKey().getName() + "\n");
                    System.out.println("\t" + pair.getValue() + "\t");
                    break;
                }
            }
        }
        if(!check){
            throw new IOException("Id store doesn't exist!");
        }
    }

    private Customer checkIfIdExistInCostumers(UUID id) throws IOException{
        boolean check = false;
        Customer customer = null;
        for(Map.Entry<UUID, Customer> pair : costumersList.entrySet()) {
            if(id.equals(pair.getKey())){
                check = true;
                customer = pair.getValue();
            }
        }
        if(!check){
            throw new IOException("Costumer id doesn't exist!");
        }
        return customer;
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
            System.out.println("7. View all produts from a store.");
            System.out.println("8. Exit.");

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
                    Customer customer = createCostumer();
                    addCostumerInMap(customer);
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
                    viewIdStores();
                    viewProductListFromAStore();
                    break;
                case 8:
                    continueMenu = false;
                    break;
            }
        }
        menu();
    }

    private void costumerMenu(){
        boolean continueMenu = true;
        while(continueMenu){
            System.out.println("\n\tWelcome!\t\n \tYou are costumer\t");
            System.out.println("1. View all products from all stores with adresses.");
            System.out.println("2. Order each store products according to decreasing order by the price of each product. ");
            System.out.println("3. Create an review for a product and add to list.");
            System.out.println("4. View all reviews for a product.");
            System.out.println("5. Exit.");

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
                    System.out.println("Costumers Id:");
                    for(Map.Entry<UUID, Customer> pair : costumersList.entrySet()){
                        System.out.println(pair.getValue().getId() + " " + pair.getValue().getFirstName() + " "+ pair.getValue().getLastName());
                    }

                    for(Map.Entry<UUID,Product> pair : productsMap.entrySet()){
                        System.out.println(pair.getValue().getId() + " " + pair.getValue().getName());
                    }
                    Product product = null;
                    System.out.println("Please, enter the id product: ");
//                    String idProductString = read.nextLine();
//                    UUID idProduct = UUID.fromString(idProductString);
                    boolean check = false;
                    while(!check) {
                        try {
                            String idProductString = read.nextLine();
                            UUID idProduct = UUID.fromString(idProductString);
                            product = checkIfIdExistInProducts(idProduct);
                            check = true;
                        } catch (IllegalArgumentException exception) {
                            System.out.println("Invalid id :(((. Please, enter a valid id");
                        }catch (IOException ioe){
                            System.out.println("Id product doesn't exist!\n Please, enter an existent one!");
                        }
                    }

                    Review review = createReview();
                    addReviewsMap(product,review);
                    break;
                case 4:
                    for(Map.Entry<UUID,Product> pair : productsMap.entrySet()){
                        System.out.println(pair.getValue().getId() + " " + pair.getValue().getName());
                    }
                    viewAllReviewsForAProduct();
                    break;

                case 5:
                    continueMenu = false;
                    break;
            }
        } menu();
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

    private Address createAnAdress(){
        System.out.println("Please, enter a city: ");
        String city = read.nextLine();
        System.out.println("Please, enter a street: ");
        String street = read.nextLine();
        System.out.println("Please, enter street number: ");
        int number = read.nextInt();
        read.nextLine();
        System.out.println("Please, enter county city: ");
        String county = read.nextLine();

        return new Address(city,street,number,county);
    }

    private void addAdressInList(Address address){
        adressesList.add(address);
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

    private Customer createCostumer(){
        System.out.println("Please, enter the firstname of costumer: ");
        String firstName = read.nextLine();
        System.out.println("Please, enter the lastname of costumer: ");
        String lastName = read.nextLine();
        System.out.println("Do you want to add your adress? (Y/N)");
        Address address = null;
        Card card = null;
        char choiceHere = checkYesOrNo();
        if(choiceHere=='Y' || choiceHere == 'y')
            address = createAnAdress();
        System.out.println("Do you want to add your card? (Y/N)");
        choiceHere = checkYesOrNo();
        if(choiceHere=='Y' || choiceHere == 'y')
            card = createACard();
        return new Customer(firstName,lastName, address,card);
    }

    private Order createOrderAdmin(){
        System.out.println("Do you want to add a new costumer? (Y/N)");
        Customer customer = null;
        char choiceHere = checkYesOrNo();
        if(choiceHere=='Y' || choiceHere == 'y')
        {
            customer = createCostumer();
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


        return new Order(customer,paymentMethod,thisStore,price,products);
    }

    private Review createReview(){
        System.out.println("Please, enter your id:");
        Customer customer = null;
        boolean check = false;
        while(!check) {
            try {
                String idCostumerString = read.nextLine();
                UUID idCostumer = UUID.fromString(idCostumerString);
                customer = checkIfIdExistInCostumers(idCostumer);
                check = true;
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid id :(((. Please, enter a valid id");
            }catch (IOException ioe){
                System.out.println("Id costumer doesn't exist!");
            }
        }

        System.out.println("Please, enter a mesaje for this product.");
        String mesaje = read.nextLine();
        System.out.println("Please, add a note for this product (1->10)");
        int numberStars = read.nextInt();
        read.nextLine();
        return new Review(customer,mesaje,numberStars);
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
            double price = read.nextDouble();
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
            Instrument i = new Instrument();
            return new Instrument(name,review,price,manufacturer,category,description,yearCreated,warranty);

        }else{
            System.out.println("Please, enter the name of product: ");
            String name = read.nextLine();
            Review review = null;
            System.out.println("Please, enter price product: ");
            double price = read.nextDouble();
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
            return new Accessory(name,review,price,manufacturer,category,description,gift);
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

    private  ArrayList<Map<Integer, Integer>> addProgram(){
        ArrayList<Map<Integer, Integer>> program = new ArrayList<Map<Integer, Integer>>(7);
        program.add(0,null);
        for(int day = 0;day<7;day++){
            int openAt = read.nextInt();
            read.nextLine();
            int closingAt = read.nextInt();
            read.nextLine();
            Map<Integer,Integer> interval = new HashMap<>(openAt,closingAt);
            program.add(day,interval);
        }
        return program;
    }

    private Store createAStore(){
        System.out.println("Please, enter the name of the store: ");
        String name = read.nextLine();
        Address address = createAnAdress();
        System.out.println("Please, enter the status of the store (open/closed):");
        String status = read.nextLine();
        int numberProducts = 0;
        ArrayList<Map<Integer, Integer>> program = null;
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
                addProductInList(createAProduct());
            }

        }
        System.out.println("Do you want to add a program for each day of the week?(Y/N)");
        choice = checkYesOrNo();
        if(choice=='Y' || choice=='y'){
            program = addProgram();
        }
        return new Store(name, address,numberProducts,status,program,productsList);
    }

    private void addStoreInList(Store store){
        UUID id = store.getId();
        storesList.put(id,store);
    }

    private void addAStoreInListWithProducts(){
        for(Map.Entry<UUID,Store> pair : storesList.entrySet()){
            Map<Store, ArrayList<Product>> pairStoreProductsList = new HashMap<Store,ArrayList<Product>>();
            pairStoreProductsList.put(pair.getValue(),pair.getValue().getProducts());
            storeWithProductsList.add(pairStoreProductsList);
        }
    }

    private void addCostumerInMap(Customer customer){
        UUID id = customer.getId();
        costumersList.put(id, customer);
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

        if(productWithReviewas.get(product)==null){
            ArrayList<Review> reviews = new ArrayList<>();
            reviews.add(review);
            productWithReviewas.put(product,reviews);
        }
        else
            productWithReviewas.get(product).add(review);
    }

    private void viewIdStores(){
        for(Map.Entry<UUID,Store> pair : storesList.entrySet()){
            System.out.println(pair.getValue().getName() +" " + pair.getKey());
        }
    }

    private void viewProductListFromAStore(){
        System.out.println("Please, enter id store: ");

        boolean check = false;
        while(!check) {
            try {
                String idStoreString = read.nextLine();
                UUID idStore = UUID.fromString(idStoreString);
                checkIfIdExistInStores(idStore);
                check = true;
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid id :(((. Please, enter a valid id");
            }catch (IOException ioe){
                System.out.println("Id store doesn't exist!\n Please, enter an existent one!");
            }
        }
    }

    private void viewAllReviewsForAProduct(){
        System.out.println("Please, enter product id.");
        Product product= null;
        boolean check = false;
        while(!check) {
            try {
                String idProductString = read.nextLine();
                UUID idProduct = UUID.fromString(idProductString);
                product = checkIfIdExistInProducts(idProduct);
                check = true;
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid id :(((. Please, enter a valid id");
            }catch (IOException ioe){
                System.out.println("Id product doesn't exist!\n Please, enter an existent one!");
            }
        }
        for(Map.Entry<Product,ArrayList<Review>> pair : productWithReviewas.entrySet()){
            if((pair.getKey().getId()).equals(product.getId())){
                System.out.println("\t"+pair.getKey()+"\t");
                System.out.println(pair.getValue());
            }
        }
    }

}
