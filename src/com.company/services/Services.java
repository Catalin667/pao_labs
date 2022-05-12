package com.company.services;

import com.company.comparator.ProductComparator;
import com.company.serialization.ReadFromCSV;
import com.company.serialization.WriteInCSV;
import com.company.stores.Address;
import com.company.stores.Store;
import com.company.stores.Voucher;
import com.company.stores.orders.Card;
import com.company.stores.orders.Order;
import com.company.stores.products.Accessory;
import com.company.stores.products.Instrument;
import com.company.stores.products.Product;
import com.company.stores.products.Review;
import com.company.users.Admin;
import com.company.users.Customer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class Services {
    private static Services instance;
    private static final Scanner read = new Scanner(System.in);
    private static final AuditService auditService = AuditService.createAuditService();
    private List<Product> productsList = new ArrayList<>();
    private Map<UUID,Product>productsMap = new HashMap<>();
    private Map<UUID, Customer> customerMap = new HashMap<>();
    private List<Address> adressesList = new ArrayList<>();
    private Map<UUID, Store> storesList = new HashMap<>();
    private Map<UUID, Order> orderList = new HashMap<>();
    private Map<UUID, Voucher> voucherMap = new HashMap<>();
    private List<Map<Store, List<Product>>> storeWithProductsList = new ArrayList<>();
    private Map<Product,List<Review>> productWithReviewas = new HashMap<>();
    private Map<UUID,Card> cardMap = new HashMap<>();
    private Map<UUID,Address>addressMap = new HashMap<>();
    private Services() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        init();
        menu();
    }

    public static Services createServices() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        if (instance == null) {
            instance = new Services();
        }
        return instance;
    }


    private void init() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Address a1 = new Address("Brasov","Sforii",1,"Brasov");
        Address a2=  new Address("Brasov","Toamnei",5,"Brasov");
        Address a3 = new Address("Sibiu","Buhusi",2,"Sibiu");
        Address a4 = new Address("Cluj","Mica",31, "Cluj");

        //////////////////////////////////////////////////// READ FROM ADDRESSFILE ///////////////////////////////////////////////
        adressesList  = ReadFromCSV.getInstance(Address.class,"src/com.company/resorces/AddressFile.csv");
        for(Address address:adressesList){
            addressMap.put(address.getId(),address);
        }

        //////////////////////////////////////////////////// READ FROM INSTRUMENTFILE////////////////////////////////////////////

        List<Instrument> instruments = new ArrayList<>();
        instruments = ReadFromCSV.getInstance(Instrument.class,"src/com.company/resorces/InstrumentsFile.csv");
        productsList.addAll(instruments);

        ////////////////////////////////////////////////// READ FROM ACCESSORYFILE //////////////////////////////////////////////

        List<Accessory> accessories = new ArrayList<>();
        accessories = ReadFromCSV.getInstance(Accessory.class,"src/com.company/resorces/AccessoryFile.csv");
        productsList.addAll(accessories);

        for(Product product:productsList){
            productsMap.put(product.getId(),product);
        }
        Map<String,String>p1 =  new HashMap<>();  
        Map<String,String>p2 =  new HashMap<>();
        p1.put("08:00","19:00");
        p2.put("9:00","15:00");
        List<Map<String, String>> program1 = new ArrayList<>(List.of(p1,p1,p1,p1,p1,p2,p2));
        ///////////////////////////////////////////////////  READ FROM STOREFILE ////////////////////////////////////////////////
        List<Store> stores = new ArrayList<>();
        stores  = ReadFromCSV.getInstance(Store.class,"src/com.company/resorces/StoreFile.csv");
        for(Store store: stores){
            store.setProducts(productsList);
            store.setNumberProducts(productsList.size());
            store.setProgram(program1);
            storesList.put(store.getId(),store);
        }
        
        //////////////////////////////////////////////////  READ FROM CUSTOMERFILE ///////////////////////////////////////////////
        List<Customer> customers = new ArrayList<>();
        customers  = ReadFromCSV.getInstance(Customer.class,"src/com.company/resorces/CustomerFile.csv");
        for(Customer customer:customers){
            customerMap.put(customer.getId(),customer);
        }

        ////////////////////////////////////////////////// READ FROM VOUCHERFILE //////////////////////////////////////////////////
        List<Voucher> voucherList = new ArrayList<>();
        voucherList  = ReadFromCSV.getInstance(Voucher.class,"src/com.company/resorces/VoucherFile.csv");
        for(Voucher voucher:voucherList){
            voucherMap.put(voucher.getId(),voucher);
        }

        /////////////////////////////////////////////////  READ FROM CARDFILE /////////////////////////////////////////////////////
        List<Card> cards = new ArrayList<>();
        cards  = ReadFromCSV.getInstance(Card.class,"src/com.company/resorces/CardFile.csv");
        for(Card card:cards){
            cardMap.put(card.getId(),card);
        }

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

    private void checkValidInputForCreation(int number) throws IOException{
        if(number<0) {
            throw new IOException("Please, enter a positive number!");
        }
    }

    private int readNumber(){
        boolean check = false;
        int number=0;
        while (!check) {
            try {
                number = read.nextInt();
                read.nextLine();
                checkValidInputForCreation(number);
                check = true;
            } catch (InputMismatchException ime) {
                System.out.println("Please enter a valid number.");
                read.nextLine();
            } catch( IOException ioe){
                System.out.println("Please, enter a positive number!");
            }
        }
        return number;
    }
    private void checkValidInputForPrice(double price) throws IOException{
        if(price<0){
            throw new IOException("Please, enter a positive number!");
        }
    }
    private double checkValidDoubleNumber(){
        boolean check = false;
        double number=0;
        while (!check) {
            try {
                number = read.nextDouble();
                read.nextLine();
                checkValidInputForPrice(number);
                check = true;
            } catch (InputMismatchException ime) {
                System.out.println("Please enter a valid price.");
                read.nextLine();
            } catch( IOException ioe){
                System.out.println("Please, enter a positive price!");
            }
        }
        return number;
    }

    private Product checkIfIdExistInProducts(UUID id) throws IOException{
        Product product = null;
        boolean check = false;
        if(!productsMap.containsKey(id)) {
            throw new IOException("Id store doesn't exist!");
        }else{
            product = productsMap.get(id);
        }
        return product;
    }
    
    private void checkValidStatus(String status) throws IOException{
        if(!("open".equals(status) || "closed".equals(status) || "OPEN".equals(status) || "CLOSED".equals(status))){
            throw new IOException("Invalid Status!");
        }
    }

    private void checkValidString(String value) throws IOException{
        if (value.equals("")){
            throw new IOException("Invalid string!");
        }
    }
    
    private String readString(){
        boolean check = false;
        String string ="";
        while (!check) {
            try {
                string = read.nextLine();
                checkValidString(string);
                check = true;
            } catch( IOException ioe){
                System.out.println("Please, enter a valid value!");
            }
        }
        return string;
    }

    private void checkValidPaymentMethod(String value) throws IOException{
        if(!value.equalsIgnoreCase("Card") && !value.equalsIgnoreCase("Cash")){
            throw new IOException("Invalid input!");
        }
    }

    private String readPaymentMethod(){
        boolean check = false;
        String string ="";
        while (!check) {
            try {
                string = read.nextLine();
                checkValidPaymentMethod(string);
                check = true;
            } catch( IOException ioe){
                System.out.println("Please, enter a valid payment method!(cash/card)");
            }
        }
        return string;
    }

    private String readStatus(){
        boolean check = false;
        String status = "";
        while (!check) {
            try {
                status = read.nextLine();
                checkValidStatus(status);
                check = true;
            } catch( IOException ioe){
                System.out.println("Please, enter a valid status!(open/closed)");
            }
        }
        return status;
    }

    private void checkValidHourForProgram(String hour) throws IOException{
        if(!Pattern.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]", hour)){
            throw new IOException("Invalid input!");
        }
    }

    private String readHourForProgram(){
        boolean check = false;
        String hour = "";
        while (!check) {
            try {
                hour = read.nextLine();
                checkValidHourForProgram(hour);
                check = true;
            } catch( IOException ioe){
                System.out.println("Please, enter a valid hour!(hh:mm, where 00=<hh<24 and 00<mm<60)");
            }
        }
        return hour;
    }

    private void checkValidYear(int year) throws IOException{
        if(year<1850 || year>2022){
            throw new IOException("Invalid input!");
        }
    }

    private void checkValidcardNumber(String cardNumber) throws IOException{
        if(!Pattern.matches("([0-9]{16})", cardNumber)){
            throw new IOException("Invalid input!");
        }
    }
    private String readCardNumber(){
        boolean check = false;
        String cardNumber = "";
        while (!check) {
            try {
                cardNumber = read.nextLine();
                checkValidcardNumber(cardNumber);
                check = true;
            } catch( IOException ioe){
                System.out.println("Please, enter a valid card number!(16 digits)");
            }
        }
        return cardNumber;
    }

    private int readYear(){
        boolean check = false;
        int year = 0;
        while (!check) {
            try {
                year = read.nextInt();
                read.nextLine();
                checkValidYear(year);
                check = true;
            }  catch(InputMismatchException ime){
                System.out.println("Please, enter a valid year!(Not a string)");
                read.nextLine();
            }catch( IOException ioe){
                System.out.println("Please, enter a valid year!(1850<year<2022)");
            }
        }
        return year;
    }

    private void checkIfIdExistInStores(UUID id) throws IOException{
        boolean check = false;
        for(Map<Store, List<Product>> pairMap : storeWithProductsList) {
            for(Map.Entry<Store, List<Product>> pair: pairMap.entrySet()){
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

    private Store checkIfTheStoreExist(UUID id) throws IOException{
        if(!storesList.containsKey(id)){
            throw new IOException("Invalid Id!");
        }else{
            return storesList.get(id);
        }
    }

    private void checkValidDate(String date) throws IOException{
        if(!Pattern.matches("(3[0-1]|[1-2][0-9]|0[1-9]).(1[0-2]|0[1-9]).[0-9]{4}",date)){
            throw new IOException("Invalid input!");
        }
    }

    private String readDate(){
        boolean check = false;
        String string ="";
        while (!check) {
            try {
                string = read.nextLine();
                checkValidDate(string);
                check = true;
            } catch( IOException ioe){
                System.out.println("Please, enter a valid date!(dd.mm.yyyy)");
            }
        }
        return string;
    }

    private void checkValidAuthorisedSignature(String value) throws IOException{
        if(!Pattern.matches("[0-9]{6}",value)) {
            throw new IOException("Invalid input!");
        }
    }

    private String readAuthorisedSignature(){
        boolean check = false;
        String string ="";
        while (!check) {
            try {
                string = read.nextLine();
                checkValidAuthorisedSignature(string);
                check = true;
            } catch( IOException ioe){
                System.out.println("Incorrect! Please, enter the authorised signature!(6 digits");
            }
        }
        return string;
    }

    private Customer checkIfIdExistInCostumers(UUID id) throws IOException{
        boolean check = false;
        Customer customer = null;
        for(Map.Entry<UUID, Customer> pair : customerMap.entrySet()) {
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
            String s1 ="1. Create a store and add this in list and add each adress in list";
            String s2 ="2. Create a product and add this in list";
            String s3 ="3. Create a costumer and add this in list";
            String s4 ="4. Register a order into a store and add this in list";
            String s5 ="5. Order each store products according to decreasing order by the price of each product";
            String s6 ="6. Register a voucher in vouchers list";
            String s7 ="7. View all produts from a store";
            String s8 ="8. Exit";

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
                    addAStoreWithProductLists(store);

                    try {
                        WriteInCSV.writeInCSV(Store.class,"src/com.company/resorces/StoreFile.csv",store);
                    } catch (IOException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    auditService.action("Admin","Option: "+s1);
                    break;
                case 2:
                    Product product = createAProduct();
                    addProductInList(product);
                    auditService.action("Admin","Option: "+s2);
                    break;
                case 3:
                    Customer customer = createCostumer();
                    try {
                        WriteInCSV.writeInCSV(Customer.class,"src/com.company/resorces/CustomerFile.csv",customer);
                    } catch (IOException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    addCostumerInMap(customer);
                    auditService.action("Admin","Option: "+s3);
                    break;
                case 4:
                    Order order = createOrderAdmin();
                    addOrderInMap(order);
                    auditService.action("Admin","Option: "+s4);
                    break;
                case 5:
                    orderStories();
                    auditService.action("Admin","Option: "+s5);
                    break;
                case 6:
                    Voucher voucher = createVoucher();
                    try {
                        WriteInCSV.writeInCSV(Voucher.class,"src/com.company/resorces/VoucherFile.csv",voucher);
                    } catch (IOException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    addVoucherInMap(voucher);
                    auditService.action("Admin","Option: "+s6);
                    break;
                case 7:
                    viewIdStores();
                    viewProductListFromAStore();
                    auditService.action("Admin","Option: "+s7);
                    break;
                case 8:
                    auditService.action("Admin","Option: "+s8);
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

            String s1 = "1. View all products from all stores with adresses";
            String s2 = "2. Order each store products according to decreasing order by the price of each product";
            String s3 = "3. Create an review for a product and add to list";
            String s4 = "4. View all reviews for a product";
            String s5 = "5. View all products with a price lower then 800";
            String s6 = "6. Exit";

            System.out.println("1. View all products from all stores with adresses.");
            System.out.println("2. Order each store products according to decreasing order by the price of each product. ");
            System.out.println("3. Create an review for a product and add to list.");
            System.out.println("4. View all reviews for a product.");
            System.out.println("5. View all products with a price lower then 800.");
            System.out.println("6. Exit.");

            System.out.println("Please, enter an option: ");
            int choice = read.nextInt();
            read.nextLine();
            switch(choice){
                case 1:
                    viewAllProducts();
                    auditService.action("Customer","Option: "+s1);
                    break;
                case 2:
                    orderStories();
                    auditService.action("Customer","Option: "+s2);
                    break;
                case 3:
                    System.out.println("Costumers Id:");
                    for(Map.Entry<UUID, Customer> pair : customerMap.entrySet()){
                        System.out.println(pair.getValue().getId() + " " + pair.getValue().getFirstName() + " "+ pair.getValue().getLastName());
                    }
                    System.out.println("\n");
                    for(Map.Entry<UUID,Product> pair : productsMap.entrySet()){
                        System.out.println(pair.getValue().getId() + " " + pair.getValue().getName());
                    }
                    Product product = null;
                    System.out.println("Please, enter the id product: ");
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
                    List<Review> listReviws = new ArrayList<>();
                    if(product.getReview()!=null){
                        listReviws = product.getReview();
                        listReviws.add(review);
                    }else{
                        listReviws.add(review);
                    }
                    product.setReview(listReviws);
                    addReviewsMap(product,review);
                    auditService.action("Customer","Option: "+s3);
                    break;

                case 4:
                    for(Map.Entry<UUID,Product> pair : productsMap.entrySet()){
                        System.out.println(pair.getValue().getId() + " " + pair.getValue().getName());
                    }
                    viewAllReviewsForAProduct();
                    auditService.action("Customer","Option: "+s4);
                    break;
                case 5:
                    viewProducts();
                    auditService.action("Customer","Option: "+s5);
                    break;
                case 6:
                    auditService.action("Customer","Option: "+s6);
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
            auditService.action("Admin","Connection + Menu");
        }else {
            costumerMenu();
            auditService.action("Customer","Menu");
        }
    }

    private Voucher createVoucher(){
        System.out.println("Please, enter voucher type: ");
        String voucherType = readString();
        System.out.println("Please, enter mesaje: ");
        String mesaje = readString();
        System.out.println("Please, enter voucher value: ");
        double value =  checkValidDoubleNumber();


        return new Voucher(voucherType,mesaje,value);
    }

    private Address createAnAdress(){
        System.out.println("Please, enter a city: ");
        String city = readString();
        System.out.println("Please, enter a street: ");
        String street =  readString();
        System.out.println("Please, enter street number: ");
        int number = readNumber();
        System.out.println("Please, enter county city: ");
        String county =  readString();

        return new Address(city,street,number,county);
    }

    private void addAdressInList(Address address){
        adressesList.add(address);
    }

    private Card createACard(){
        System.out.println("Please, enter card number: ");
        String  number = readCardNumber();
        System.out.println("Please, enter card valid thru date (mm.dd.yyyy): ");
        String date = readDate();
        Date validThru = null;
        boolean check = false;
        while(!check){
            try {
                validThru = new SimpleDateFormat("dd.MM.yyyy").parse(date);
                check = true;
            } catch (ParseException e) {
                System.out.println("Please, enter card valid thru date (mm.dd.yyyy): ");
                date = readDate();
            }
        }

        System.out.println("Please, enter card type: ");
        String type = readString();
        System.out.println("Please, enter authorised signature: ");
        String authorisedSignatureHelp = readAuthorisedSignature();
        int authorisedSignature = Integer.parseInt(authorisedSignatureHelp);
        return new Card(number,validThru,type,authorisedSignature);
    }

    private Customer createCostumer(){
        System.out.println("Please, enter the firstname of costumer: ");
        String firstName = readString();
        System.out.println("Please, enter the lastname of costumer: ");
        String lastName =  readString();
        System.out.println("Do you want to add your adress? (Y/N)");
        char choiceHere = checkYesOrNo();
        Address address = null;
        Card card = null;
        if(choiceHere=='Y' || choiceHere == 'y')
            address = createAnAdress();
            try {
                WriteInCSV.writeInCSV(Address.class,"src/com.company/resorces/AddressFile.csv",address);
            } catch (IOException | IllegalAccessException e) {
                e.printStackTrace();
            }
        System.out.println("Do you want to add your card? (Y/N)");
        choiceHere = checkYesOrNo();
        if(choiceHere=='Y' || choiceHere == 'y')
            card = createACard();
            try {
                WriteInCSV.writeInCSV(Card.class,"src/com.company/resorces/CardFile.csv",card);
            } catch (IOException | IllegalAccessException e) {
                e.printStackTrace();
            }
        return new Customer(firstName,lastName, address,card);
    }

    private void printStoreList(){
        for(Map.Entry<UUID,Store> pair : storesList.entrySet()){
            System.out.println(pair.getKey() + " " +  pair.getValue().getName() + '\n');
        }
    }

    private void printIdProductsFromThisStore(UUID id){
        System.out.println("ID  " + id);
        List<Product>products = storesList.get(id).getProducts();
        for(Product product: products){
            System.out.println( product.getName() + " " + product.getId() + "\n");
        }
    }

    private Order createOrderAdmin(){
        System.out.println("Do you want to add a new costumer? (Y/N)");
        Customer customer = null;
        char choiceHere = checkYesOrNo();
        if(choiceHere=='Y' || choiceHere == 'y')
        {
            customer = createCostumer();
            try {
                WriteInCSV.writeInCSV(Customer.class,"src/com.company/resorces/CustomerFile.csv",customer);
            } catch (IOException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Please, enter payment method (Card/Cash): ");
        String paymentMethod = readPaymentMethod();
        printStoreList();
        System.out.println("\tPlease, enter the id store:\t");
        Store thisStore = null;
        UUID idStore = null;
        boolean check = false;
        while(!check) {
            try {
                String idStoreString = read.nextLine();
                idStore = UUID.fromString(idStoreString);
                thisStore = checkIfTheStoreExist(idStore);
                check = true;
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid id :(((. Please, enter a valid id");
            }catch (IOException ioe){
                System.out.println("Id store doesn't exist!\n Please, enter an existent one!");
            }
        }

        System.out.println("Please, enter the number of products which you want to buy: ");
        int numberProducts = readNumber();
        List<Product> products = new ArrayList<>(numberProducts);
        List<UUID> idList  = new ArrayList<>(numberProducts);
        if(numberProducts>0){
            printIdProductsFromThisStore(idStore);
        }

        for(int i=0;i<numberProducts;i++){
            check = false;
            while(!check){
                System.out.println("Please, enter the id product: ");
                try {
                    String idProductString = read.nextLine();
                    UUID idProduct = UUID.fromString(idProductString);
                    if (!productsMap.containsKey(idProduct)){
                        System.out.println("Invalid product id!");
                    }else {
                        idList.add(i, idProduct);
                        check = true;
                    }
                }catch(IllegalArgumentException e){
                    System.out.println("Invalid id :(((. Please, enter a valid id");
                }
            }
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
        String mesaje = readString();
        System.out.println("Please, add a note for this product (1->10)");
        int numberStars =readNumber();
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
            String name = readString();
            List<Review> review = new ArrayList<>();
            System.out.println("Please, enter price product: ");
            double price = checkValidDoubleNumber();
            System.out.println("Please, enter the year of production: ");
            int yearCreated = readYear();
            System.out.println("Please, enter the period warranty(years)");
            int warranty = readNumber();
            System.out.println("Please, enter the manufacturer: ");
            String manufacturer = readString();
            System.out.println("Please, enter the category: ");
            String category = readString();
            System.out.println("Please, enter a description: ");
            String description = readString();
            Instrument instrument = new Instrument(name,review,price,manufacturer,category,description,yearCreated,warranty);
            try {
                WriteInCSV.writeInCSV(Instrument.class,"src/com.company/resorces/InstrumentsFile.csv",instrument);
            } catch (IOException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return instrument;
        }else{
            System.out.println("Please, enter the name of product: ");
            String name = readString();
            List<Review> review = new ArrayList<>();
            System.out.println("Please, enter price product: ");
            double price = checkValidDoubleNumber();
            System.out.println("Please, enter the manufacturer: ");
            String manufacturer = readString();
            System.out.println("Please, enter the category: ");
            String category =  readString();
            boolean gift = false;
            System.out.println("Please, choose if this accessory is a gift or no.(Y/N)");
            char choiceHere = checkYesOrNo();
            if(choiceHere=='Y' || choiceHere == 'y')
                gift = true;
            System.out.println("Please, enter a description: ");
            String description = readString();
            Accessory accessory = new Accessory(name,review,price,manufacturer,category,description,gift);
            try {
                WriteInCSV.writeInCSV(Accessory.class,"src/com.company/resorces/AccessoryFile.csv",accessory);
            } catch (IOException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return accessory;
        }
    }

    private void addProductInList(Product product){
        productsList.add(product);
    }

        private void yesOrNo(String value) throws IOException{
        if(!value.equals("Y") && !value.equals("N") && !value.equals("y") && !value.equals("n"))
            throw new IOException("Invalid input!");
    }

    private char checkYesOrNo(){
        boolean check = false;
        String choice = "";
        while (!check) {
            try {
                choice =  read.nextLine();
                yesOrNo(choice);
                check = true;
            } catch (InputMismatchException ime) {
                System.out.println("Please enter a valid answer.(Y/y or N/n)");
            }catch( IOException ioe){
                System.out.println("Please enter Y/y or N/n");
            }
        }
        return choice.charAt(0);
    }

    private  List<Map<String, String>> addProgram(){
        List<Map<String, String>> program = new ArrayList<>(7);
        program.add(0,null);
        for(int day = 0;day<7;day++){
            System.out.println("Please, enter open hour.");
            String openAt = readHourForProgram();
            System.out.println("Please, enter closing hour.");
            String closingAt = readHourForProgram();
            Map<String,String> interval = new HashMap<>();
            interval.put(openAt,closingAt);
            program.add(day,interval);
        }
        return program;
    }

    private Store createAStore(){
        System.out.println("Please, enter the name of the store: ");
        String name= readString();
        Address address = createAnAdress();
        try {
            WriteInCSV.writeInCSV(Address.class,"src/com.company/resorces/AddressFile.csv",address);
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println("Please, enter the status of the store (open/closed):");
        String status = readStatus();
        int numberProducts = 0;
        List<Map<String, String>> program = null;
        List<Product> productsListForThis = new ArrayList<>();
        System.out.println("Do you want to add a list of products? (Y/N)");
        char choice = checkYesOrNo();
        if(choice == 'Y' || choice =='y')
        {
            System.out.println("How many products would you like to add? ");
            numberProducts = readNumber();
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
            Map<Store, List<Product>> pairStoreProductsList = new HashMap<>();
            pairStoreProductsList.put(pair.getValue(),pair.getValue().getProducts());
            storeWithProductsList.add(pairStoreProductsList);
        }
    }

    private void addAStoreWithProductLists(Store store){
        Map<Store,List<Product>> storeWithProductList = new HashMap<>();
        storeWithProductList.put(store,store.getProducts());
        storeWithProductsList.add(storeWithProductList);
    }

    private void addCostumerInMap(Customer customer){
        UUID id = customer.getId();
        customerMap.put(id, customer);
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
            List<Product> products = pair.getValue().getProducts();
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
            List<Review> reviews = new ArrayList<>();
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
        for(Map.Entry<Product,List<Review>> pair : productWithReviewas.entrySet()){
            if((pair.getKey().getId()).equals(product.getId())){
                System.out.println("\t"+pair.getKey()+"\t");
                System.out.println(pair.getValue());
            }
        }
    }

        private void viewProducts(){
            productsMap.entrySet().stream()
                    .filter(map->800>map.getValue().getPrice())
                    .forEach(System.out::println);
        }
}