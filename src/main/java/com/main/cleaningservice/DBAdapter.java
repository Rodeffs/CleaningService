package com.main.cleaningservice;

import java.sql.*;
import java.util.ArrayList;

public class DBAdapter {
    private Connection conn;

    public void connect() {
        try {
            conn = DriverManager.getConnection("jdbc:postgresql:cleaning_service");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeStatement(String sql) throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute(sql);
        statement.close();
    }

    // ACCOUNT FUNCTIONS

    public void insertAccount(String login, String password, AccountType accountType, String displayName) throws SQLException {
        String sql = "INSERT INTO account(login, password, account_type_id, display_name) VALUES ('" + login + "', '" + password + "', " + accountType.getId() + ", '" + displayName + "')";
        executeStatement(sql);
        System.out.println("Account added");
    }

    public void insertAccount(String login, String password, String displayName) throws SQLException {
        String sql = "INSERT INTO account(login, password, display_name) VALUES ('" + login + "', '" + password + "', '" + displayName + "')";
        executeStatement(sql);
        System.out.println("Account added");
    }

    public void updateAccountLogin(Account account, String newLogin) throws SQLException {
        String sql = "UPDATE account SET login = '" + newLogin + "' WHERE account_id = " + account.getId();
        executeStatement(sql);
        account.setLogin(newLogin);
        System.out.println("Account " + account.getId() + " login changed");
    }

    public void updateAccountPassword(Account account, String newPassword) throws SQLException {
        String sql = "UPDATE account SET password = '" + newPassword + "' WHERE account_id = " + account.getId();
        executeStatement(sql);
        account.setPassword(newPassword);
        System.out.println("Account " + account.getId() + " password changed");
    }

    public void updateAccountType(Account account, AccountType accountType) throws SQLException {
        String sql = "UPDATE account SET account_type_id = " + accountType.getId() + " WHERE account_id = " + account.getId();
        executeStatement(sql);
        account.setType(accountType);
        System.out.println("Account " + account.getId() + " type changed");
    }

    public void updateAccountDisplayName(Account account, String newDisplayName) throws SQLException {
        String sql = "UPDATE account SET display_name = '" + newDisplayName + "' WHERE account_id = " + account.getId();
        executeStatement(sql);
        account.setDisplayName(newDisplayName);
        System.out.println("Account " + account.getId() + " display name changed");
    }

    public void deleteAccount(Account account) throws SQLException {
        String sql = "DELETE FROM account WHERE account_id = " + account.getId();
        executeStatement(sql);
        System.out.println("Account " + account.getId() + " deleted");
    }

    public Account selectAccount(int accountId) throws SQLException {
        Account account = null;

        String sql = "SELECT * FROM account WHERE account_id = " + accountId;

        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            String login = rs.getString("login");
            String password = rs.getString("password");
            AccountType accountType = selectAccountType(rs.getInt("account_type_id"));
            String displayName = rs.getString("display_name");
            account = new Account(accountId, login, password, accountType, displayName);
        }

        rs.close();
        statement.close();
        return account;
    }

    public Account selectAccount(String login) throws SQLException {
        Account account = null;

        String sql = "SELECT * FROM account WHERE login = '" + login + "'";

        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            int id = rs.getInt("account_id");
            String password = rs.getString("password");
            AccountType accountType = selectAccountType(rs.getInt("account_type_id"));
            String displayName = rs.getString("display_name");
            account = new Account(id, login, password, accountType, displayName);
        }

        rs.close();
        statement.close();
        return account;
    }

    public ArrayList<Account> selectAccounts() throws SQLException {
        ArrayList<Account> accounts = new ArrayList<Account>();

        String sql = "SELECT * FROM account";

        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("account_id");
            String login = rs.getString("login");
            String password = rs.getString("password");
            AccountType accountType = selectAccountType(rs.getInt("account_type_id"));
            String displayName = rs.getString("display_name");
            accounts.add(new Account(id, login, password, accountType, displayName));
        }

        rs.close();
        statement.close();
        return accounts;
    }

    // ACCOUNT TYPE FUNCTIONS

    public AccountType selectAccountType(int accountTypeId) throws SQLException {
        AccountType accountType = null;

        String sql = "SELECT * FROM account_type WHERE account_type_id = " + accountTypeId;

        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            String name = rs.getString("account_type_name");
            accountType = new AccountType(accountTypeId, name);
        }

        rs.close();
        statement.close();
        return accountType;
    }

    public ArrayList<AccountType> selectAccountTypes() throws SQLException {
        ArrayList<AccountType> accountTypes = new ArrayList<AccountType>();

        String sql = "SELECT * FROM account_type";

        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("account_type_id");
            String name = rs.getString("account_type_name");
            accountTypes.add(new AccountType(id, name));
        }

        rs.close();
        statement.close();
        return accountTypes;
    }

    // ADDRESS FUNCTIONS

    public void insertAddress(Street street, int buildingNumber, int entranceNumber, int floorNumber, int unitNumber) throws SQLException {
        String sql = "INSERT INTO address(street_id, building_number, entrance_number, floor_number, unit_number) VALUES (" + street.getId() + ", " + buildingNumber + ", " + entranceNumber + ", " + floorNumber + ", " + unitNumber + ")";
        executeStatement(sql);
        System.out.println("Address added");
    }

    public void updateAddressStreet(Address address, Street street) throws SQLException {
        String sql = "UPDATE address SET street_id = " + street.getId() + " WHERE address_id = " + address.getId();
        executeStatement(sql);
        address.setStreet(street);
        System.out.println("Address " + address.getId() + " street changed");
    }

    public void updateAddressBuildingNumber(Address address, int newBuildingNumber) throws SQLException {
        String sql = "UPDATE address SET building_number = " + newBuildingNumber + " WHERE address_id = " + address.getId();
        executeStatement(sql);
        address.setBuildingNumber(newBuildingNumber);
        System.out.println("Address " + address.getId() + " building number changed");
    }

    public void updateAddressEntranceNumber(Address address, int newEntranceNumber) throws SQLException {
        String sql = "UPDATE address SET entrance_number = " + newEntranceNumber + " WHERE address_id = " + address.getId();
        executeStatement(sql);
        address.setEntranceNumber(newEntranceNumber);
        System.out.println("Address " + address.getId() + " entrance number changed");
    }

    public void updateAddressFloorNumber(Address address, int newFloorNumber) throws SQLException {
        String sql = "UPDATE address SET floor_number = " + newFloorNumber + " WHERE address_id = " + address.getId();
        executeStatement(sql);
        address.setFloorNumber(newFloorNumber);
        System.out.println("Address " + address.getId() + " floor number changed");
    }

    public void updateAddressUnitNumber(Address address, int newUnitNumber) throws SQLException {
        String sql = "UPDATE address SET unit_number = " + newUnitNumber + " WHERE address_id = " + address.getId();
        executeStatement(sql);
        address.setUnitNumber(newUnitNumber);
        System.out.println("Address " + address.getId() + " unit number changed");
    }

    public void deleteAddress(Address address) throws SQLException {
        String sql = "DELETE FROM address WHERE address_id = " + address.getId();
        executeStatement(sql);
        System.out.println("Address " + address.getId() + " deleted");
    }

    public Address selectAddress(int addressId) throws SQLException {
        Address address = null;

        String sql = "SELECT * FROM address WHERE address_id = " + addressId;
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            Street street = selectStreet(rs.getInt("street_id"));
            int buildingNumber = rs.getInt("building_number");
            int entranceNumber = rs.getInt("entrance_number");
            int floorNumber = rs.getInt("floor_number");
            int unitNumber = rs.getInt("unit_number");
            address = new Address(addressId, street, buildingNumber, entranceNumber, floorNumber, unitNumber);
        }

        rs.close();
        statement.close();
        return address;
    }

    public Address selectAddress(Street street, int buildingNumber, int entranceNumber, int floorNumber, int unitNumber) throws SQLException {
        Address address = null;

        String sql = "SELECT * FROM address WHERE street_id = " + street.getId() + " AND building_number = " + buildingNumber + " AND entrance_number = " + entranceNumber + " AND floor_number = " + floorNumber + " AND unit_number = " + unitNumber;
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            int id = rs.getInt("address_id");
            address = new Address(id, street, buildingNumber, entranceNumber, floorNumber, unitNumber);
        }

        rs.close();
        statement.close();
        return address;
    }

    public ArrayList<Address> selectAddresses() throws SQLException {
        ArrayList<Address> addresses = new ArrayList<Address>();

        String sql = "SELECT * FROM address";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("address_id");
            Street street = selectStreet(rs.getInt("street_id"));
            int buildingNumber = rs.getInt("building_number");
            int entranceNumber = rs.getInt("entrance_number");
            int floorNumber = rs.getInt("floor_number");
            int unitNumber = rs.getInt("unit_number");
            addresses.add(new Address(id, street, buildingNumber, entranceNumber, floorNumber, unitNumber));
        }

        rs.close();
        statement.close();
        return addresses;
    }

    // CITY FUNCTIONS

    public void insertCity(String name, Country country) throws SQLException {
        String sql = "INSERT INTO city(city_name, country_id) VALUES ('" + name + "', " + country.getId() + ")";
        executeStatement(sql);
        System.out.println("City added");
    }

    public void updateCityName(City city, String newName) throws SQLException {
        String sql = "UPDATE city SET city_name = '" + newName + "' WHERE city_id = " + city.getId();
        executeStatement(sql);
        city.setName(newName);
        System.out.println("City " + city.getId() + " name changed");
    }

    public void updateCityCountry(City city, Country country) throws SQLException {
        String sql = "UPDATE city SET country_id = " + country.getId() + " WHERE city_id = " + city.getId();
        executeStatement(sql);
        city.setCountry(country);
        System.out.println("City " + city.getId() + " country changed");
    }

    public void deleteCity(City city) throws SQLException {
        String sql = "DELETE FROM city WHERE city_id = " + city.getId();
        executeStatement(sql);
        System.out.println("City " + city.getId() + " deleted");
    }

    public City selectCity(int cityId) throws SQLException {
        City city = null;

        String sql = "SELECT * FROM city WHERE city_id = " + cityId;

        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            String name = rs.getString("city_name");
            Country country = selectCountry(rs.getInt("country_id"));
            city = new City(cityId, country, name);
        }

        rs.close();
        statement.close();
        return city;
    }

    public ArrayList<City> selectCities() throws SQLException {
        ArrayList<City> cities = new ArrayList<City>();

        String sql = "SELECT * FROM city";

        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("city_id");
            String name = rs.getString("city_name");
            Country country = selectCountry(rs.getInt("country_id"));
            cities.add(new City(id, country, name));
        }

        rs.close();
        statement.close();
        return cities;
    }

    public ArrayList<City> selectCities(Country country) throws SQLException {
        ArrayList<City> cities = new ArrayList<City>();

        String sql = "SELECT * FROM city WHERE country_id = " + country.getId();

        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("city_id");
            String name = rs.getString("city_name");
            cities.add(new City(id, country, name));
        }

        rs.close();
        statement.close();
        return cities;
    }

    // CLEANER FUNCTIONS

    public void insertCleaner(String name, String surname, String secondName, Date birthday) throws SQLException {
        String sql = "INSERT INTO cleaner(cleaner_name, cleaner_surname, cleaner_second_name, cleaner_birthday) VALUES ('" + name + "', '" + surname + "', '" + secondName + "', '" + birthday.toString() + "')";
        executeStatement(sql);
        System.out.println("Cleaner added");
    }

    public void updateCleanerName(Cleaner cleaner, String newName) throws SQLException {
        String sql = "UPDATE cleaner SET cleaner_name = '" + newName + "' WHERE cleaner_id = " + cleaner.getId();
        executeStatement(sql);
        cleaner.setName(newName);
        System.out.println("Cleaner " + cleaner.getId() + " name changed");
    }

    public void updateCleanerSurname(Cleaner cleaner, String newSurname) throws SQLException {
        String sql = "UPDATE cleaner SET cleaner_surname = '" + newSurname + "' WHERE cleaner_id = " + cleaner.getId();
        executeStatement(sql);
        cleaner.setSurname(newSurname);
        System.out.println("Cleaner " + cleaner.getId() + " surname changed");
    }

    public void updateCleanerSecondName(Cleaner cleaner, String newSecondName) throws SQLException {
        String sql = "UPDATE cleaner SET cleaner_second_name = '" + newSecondName + "' WHERE cleaner_id = " + cleaner.getId();
        executeStatement(sql);
        cleaner.setSecondName(newSecondName);
        System.out.println("Cleaner " + cleaner.getId() + " second name changed");
    }

    public void updateCleanerBirthday(Cleaner cleaner, Date newBirthday) throws SQLException {
        String sql = "UPDATE cleaner SET cleaner_birthday = '" + newBirthday.toString() + "' WHERE cleaner_id = " + cleaner.getId();
        executeStatement(sql);
        cleaner.setBirthday(newBirthday);
        System.out.println("Cleaner " + cleaner.getId() + " birthday changed");
    }

    public void deleteCleaner(Cleaner cleaner) throws SQLException {
        String sql = "DELETE FROM cleaner WHERE cleaner_id = " + cleaner.getId();
        executeStatement(sql);
        System.out.println("Cleaner " + cleaner.getId() + " deleted");
    }

    public Cleaner selectCleaner(int cleanerId) throws SQLException {
        Cleaner cleaner = null;

        String sql = "SELECT * FROM cleaner WHERE cleaner_id = " + cleanerId;
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            String name = rs.getString("cleaner_name");
            String surname = rs.getString("cleaner_surname");
            String secondName = rs.getString("cleaner_second_name");
            Date birthday = rs.getDate("cleaner_birthday");
            cleaner = new Cleaner(cleanerId, name, surname, secondName, birthday);
        }

        rs.close();
        statement.close();
        return cleaner;
    }

    public ArrayList<Cleaner> selectCleaners() throws SQLException {
        ArrayList<Cleaner> cleaners = new ArrayList<Cleaner>();

        String sql = "SELECT * FROM cleaner";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("cleaner_id");
            String name = rs.getString("cleaner_name");
            String surname = rs.getString("cleaner_surname");
            String secondName = rs.getString("cleaner_second_name");
            Date birthday = rs.getDate("cleaner_birthday");
            cleaners.add(new Cleaner(id, name, surname, secondName, birthday));
        }

        rs.close();
        statement.close();
        return cleaners;
    }

    public ArrayList<Cleaner> selectCleaners(Cleaning cleaning) throws SQLException {
        ArrayList<Cleaner> cleaners = new ArrayList<Cleaner>();

        String sql = "SELECT cleaner_id FROM cleaning_cleaners WHERE cleaning_id = " + cleaning.getId();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next())
            cleaners.add(selectCleaner(rs.getInt(1)));

        rs.close();
        statement.close();
        return cleaners;
    }

    // CLEANING FUNCTIONS

    public void insertCleaning(Address address, PlaceType placeType, CleaningType cleaningType, Timestamp timestamp, Client client) throws SQLException {
        String sql = "INSERT INTO cleaning(address_id, place_type_id, cleaning_type_id, time_date, client_id) VALUES (" + address.getId() + ", " + placeType.getId() + ", " + cleaningType.getId() + ", '" + timestamp.toString() + "', " + client.getId() + ")";
        executeStatement(sql);
        System.out.println("Cleaning added");
    }

    public void insertCleaning(Address address, PlaceType placeType, CleaningType cleaningType, Timestamp timestamp, Client client, double totalPrice) throws SQLException {
        String sql = "INSERT INTO cleaning(address_id, place_type_id, cleaning_type_id, time_date, client_id, total_price) VALUES (" + address.getId() + ", " + placeType.getId() + ", " + cleaningType.getId() + ", '" + timestamp.toString() + "', " + client.getId() + ", " + totalPrice + ")";
        executeStatement(sql);
        System.out.println("Cleaning added");
    }

    public void updateCleaningAddress(Cleaning cleaning, Address address) throws SQLException {
        String sql = "UPDATE cleaning SET address_id = " + address.getId() + " WHERE cleaning_id = " + cleaning.getId();
        executeStatement(sql);
        cleaning.setAddress(address);
        System.out.println("Cleaning " + cleaning.getId() + " address changed");
    }

    public void updateCleaningPlaceType(Cleaning cleaning, PlaceType placeType) throws SQLException {
        String sql = "UPDATE cleaning SET place_type_id = " + placeType.getId() + " WHERE cleaning_id = " + cleaning.getId();
        executeStatement(sql);
        cleaning.setPlaceType(placeType);
        System.out.println("Cleaning " + cleaning.getId() + " place type changed");
    }

    public void updateCleaningType(Cleaning cleaning, CleaningType cleaningType) throws SQLException {
        String sql = "UPDATE cleaning SET cleaning_type_id = " + cleaningType.getId() + " WHERE cleaning_id = " + cleaning.getId();
        executeStatement(sql);
        cleaning.setCleaningType(cleaningType);
        System.out.println("Cleaning " + cleaning.getId() + " type changed");
    }

    public void updateCleaningTimestamp(Cleaning cleaning, Timestamp timestamp) throws SQLException {
        String sql = "UPDATE cleaning SET time_date = '" + timestamp.toString() + "' WHERE cleaning_id = " + cleaning.getId();
        executeStatement(sql);
        cleaning.setTimestamp(timestamp);
        System.out.println("Cleaning " + cleaning.getId() + " timestamp changed");
    }

    public void updateCleaningClient(Cleaning cleaning, Client client) throws SQLException {
        String sql = "UPDATE cleaning SET client_id = " + client.getId() + " WHERE cleaning_id = " + cleaning.getId();
        executeStatement(sql);
        cleaning.setClient(client);
        System.out.println("Cleaning " + cleaning.getId() + " client changed");
    }

    public void updateCleaningTotalPrice(Cleaning cleaning, double totalPrice) throws SQLException {
        String sql = "UPDATE cleaning SET total_price = " + totalPrice + " WHERE cleaning_id = " + cleaning.getId();
        executeStatement(sql);
        cleaning.setTotalPrice(totalPrice);
        System.out.println("Cleaning " + cleaning.getId() + " total price changed");
    }

    public void deleteCleaning(Cleaning cleaning) throws SQLException {
        String sql = "DELETE FROM cleaning WHERE cleaning_id = " + cleaning.getId();
        executeStatement(sql);
        System.out.println("Cleaning " + cleaning.getId() + " deleted");
    }

    public Cleaning selectCleaning(int cleaningId) throws SQLException {
        Cleaning cleaning = null;

        String sql = "SELECT * FROM cleaning WHERE cleaning_id = " + cleaningId;
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            Address address = selectAddress(rs.getInt("address_id"));
            PlaceType placeType = selectPlaceType(rs.getInt("place_type_id"));
            CleaningType cleaningType = selectCleaningType(rs.getInt("cleaning_type_id"));
            Timestamp timestamp = rs.getTimestamp("time_date");
            Client client = selectClient(rs.getInt("client_id"));
            cleaning = new Cleaning(cleaningId, address, placeType, cleaningType, timestamp, client);
        }

        rs.close();
        statement.close();
        return cleaning;
    }

    public Cleaning selectCleaning(Address address, PlaceType placeType, CleaningType cleaningType, Timestamp timestamp, Client client) throws SQLException {
        Cleaning cleaning = null;

        String sql = "SELECT * FROM cleaning WHERE address_id = " + address.getId() + " AND place_type_id = " + placeType.getId() + " AND cleaning_type_id = " + cleaningType.getId() + " AND time_date = '" + timestamp.toString() + "' AND client_id = " + client.getId();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            int id = rs.getInt("cleaning_id");
            int cleanersAmount = rs.getInt("cleaners_amount");
            double totalPrice = rs.getDouble("total_price");
            cleaning = new Cleaning(id, address, placeType, cleaningType, timestamp, totalPrice, cleanersAmount, client);
        }

        rs.close();
        statement.close();
        return cleaning;
    }

    public ArrayList<Cleaning> selectCleanings() throws SQLException {
        ArrayList<Cleaning> cleanings = new ArrayList<Cleaning>();

        String sql = "SELECT * FROM cleaning";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("cleaning_id");
            Address address = selectAddress(rs.getInt("address_id"));
            PlaceType placeType = selectPlaceType(rs.getInt("place_type_id"));
            CleaningType cleaningType = selectCleaningType(rs.getInt("cleaning_type_id"));
            Timestamp timestamp = rs.getTimestamp("time_date");
            Client client = selectClient(rs.getInt("client_id"));
            int cleanersAmount = rs.getInt("cleaners_amount");
            double totalPrice = rs.getDouble("total_price");
            cleanings.add(new Cleaning(id, address, placeType, cleaningType, timestamp, totalPrice, cleanersAmount, client));
        }

        rs.close();
        statement.close();
        return cleanings;
    }

    public ArrayList<Cleaning> selectCleanings(Client client) throws SQLException {
        ArrayList<Cleaning> cleanings = new ArrayList<Cleaning>();

        String sql = "SELECT * FROM cleaning WHERE client_id = " + client.getId();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("cleaning_id");
            Address address = selectAddress(rs.getInt("address_id"));
            PlaceType placeType = selectPlaceType(rs.getInt("place_type_id"));
            CleaningType cleaningType = selectCleaningType(rs.getInt("cleaning_type_id"));
            Timestamp timestamp = rs.getTimestamp("time_date");
            int cleanersAmount = rs.getInt("cleaners_amount");
            double totalPrice = rs.getDouble("total_price");
            cleanings.add(new Cleaning(id, address, placeType, cleaningType, timestamp, totalPrice, cleanersAmount, client));
        }

        rs.close();
        statement.close();
        return cleanings;
    }

    public void calculateTotalPrice(Cleaning cleaning) throws SQLException {
        String sql = "CALL calculate_total_price(" + cleaning.getId() + ")";
        executeStatement(sql);
        System.out.println("Cleaning " + cleaning.getId() + " total price calculated");
    }

    public void calculateCleanersAmount(Cleaning cleaning) throws SQLException {
        String sql = "CALL calculate_cleaners_amount(" + cleaning.getId() + ")";
        executeStatement(sql);
        System.out.println("Cleaning " + cleaning.getId() + " cleaners amount calculated");
    }

    // CLEANING SERVICES

    public void insertCleaningService(Cleaning cleaning, Service service) throws SQLException {
        String sql = "INSERT INTO cleaning_services(cleaning_id, service_id) VALUES (" + cleaning.getId() + ", " + service.getId() + ")";
        executeStatement(sql);
        System.out.println("Cleaning " + cleaning.getId() + " added service " + service.getId());
    }

    public void deleteCleaningService(Cleaning cleaning, Service service) throws SQLException {
        String sql = "DELETE FROM cleaning_services WHERE cleaning_id = " + cleaning.getId() + " AND service_id = " + service.getId();
        executeStatement(sql);
        System.out.println("Cleaning " + cleaning.getId() + " deleted service " + service.getId());
    }

    public ArrayList<Service> selectCleaningServices(Cleaning cleaning) throws SQLException {
        ArrayList<Service> services = new ArrayList<Service>();

        String sql = "SELECT * FROM cleaning_services WHERE cleaning_id = " + cleaning.getId();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("service_id");
            services.add(selectService(id));
        }

        rs.close();
        statement.close();
        return services;
    }

    // CLEANING TYPE FUNCTIONS

    public void insertCleaningType(String name) throws SQLException {
        String sql = "INSERT INTO cleaning_type(cleaning_type_name) VALUES ('" + name + "')";
        executeStatement(sql);
        System.out.println("Cleaning type added");
    }

    public void updateCleaningTypeName(CleaningType cleaningType, String newName) throws SQLException {
        String sql = "UPDATE cleaning_type SET cleaning_type_name = '" + newName + "' WHERE cleaning_type_id = " + cleaningType.getId();
        executeStatement(sql);
        cleaningType.setName(newName);
        System.out.println("Cleaning type " + cleaningType.getId() + " name changed");
    }

    public void deleteCleaningType(CleaningType cleaningType) throws SQLException {
        String sql = "DELETE FROM cleaning_type WHERE cleaning_type_id = " + cleaningType.getId();
        executeStatement(sql);
        System.out.println("Cleaning type " + cleaningType.getId() + " deleted");
    }

    public CleaningType selectCleaningType(int cleaningTypeId) throws SQLException {
        CleaningType cleaningType = null;

        String sql = "SELECT * FROM cleaning_type WHERE cleaning_type_id = " + cleaningTypeId;
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            String name = rs.getString("cleaning_type_name");
            cleaningType = new CleaningType(cleaningTypeId, name);
        }

        rs.close();
        statement.close();
        return cleaningType;
    }

    public ArrayList<CleaningType> selectCleaningTypes() throws SQLException {
        ArrayList<CleaningType> cleaningTypes = new ArrayList<CleaningType>();

        String sql = "SELECT * FROM cleaning_type";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("cleaning_type_id");
            String name = rs.getString("cleaning_type_name");
            cleaningTypes.add(new CleaningType(id, name));
        }

        rs.close();
        statement.close();
        return cleaningTypes;
    }

    // CLIENT FUNCTIONS

    public void insertClient(String name, String surname, String secondName, ClientType clientType, Account account, String email, String phone) throws SQLException {
        String sql = "INSERT INTO client(client_name, client_surname, client_second_name, client_type_id, account_id, client_email, client_phone) VALUES ('" + name + "', '" + surname + "', '" + secondName + "', " + clientType.getId() + ", " + account.getId() + ", '" + email + "', '" + phone + "')";
        executeStatement(sql);
        System.out.println("Client added");
    }

    public void updateClientName(Client client, String newName) throws SQLException {
        String sql = "UPDATE client SET client_name = '" + newName + "' WHERE client_id = " + client.getId();
        executeStatement(sql);
        client.setName(newName);
        System.out.println("Client " + client.getId() + " name updated");
    }

    public void updateClientSurname(Client client, String newSurname) throws SQLException {
        String sql = "UPDATE client SET client_surname = '" + newSurname + "' WHERE client_id = " + client.getId();
        executeStatement(sql);
        client.setSurname(newSurname);
        System.out.println("Client " + client.getId() + " surname updated");
    }

    public void updateClientSecondName(Client client, String newSecondName) throws SQLException {
        String sql = "UPDATE client SET client_second_name = '" + newSecondName + "' WHERE client_id = " + client.getId();
        executeStatement(sql);
        client.setSecondName(newSecondName);
        System.out.println("Client " + client.getId() + " second name updated");
    }

    public void updateClientType(Client client, ClientType clientType) throws SQLException {
        String sql = "UPDATE client SET client_type_id = " + clientType.getId() + " WHERE client_id = " + client.getId();
        executeStatement(sql);
        client.setType(clientType);
        System.out.println("Client " + client.getId() + " type updated");
    }

    public void updateClientAccount(Client client, Account account) throws SQLException {
        String sql = "UPDATE client SET account_id = " + account.getId() + " WHERE client_id = " + client.getId();
        executeStatement(sql);
        client.setAccount(account);
        System.out.println("Client " + client.getId() + " account updated");
    }

    public void updateClientEmail(Client client, String newEmail) throws SQLException {
        String sql = "UPDATE client SET client_email = '" + newEmail + "' WHERE client_id = " + client.getId();
        executeStatement(sql);
        client.setEmail(newEmail);
        System.out.println("Client " + client.getId() + " email updated");
    }

    public void updateClientPhone(Client client, String newPhone) throws SQLException {
        String sql = "UPDATE client SET client_phone = '" + newPhone + "' WHERE client_id = " + client.getId();
        executeStatement(sql);
        client.setPhone(newPhone);
        System.out.println("Client " + client.getId() + " phone updated");
    }

    public void deleteClient(Client client) throws SQLException {
        String sql = "DELETE FROM client WHERE client_id = " + client.getId();
        executeStatement(sql);
        System.out.println("Client " + client.getId() + " deleted");
    }

    public Client selectClient(int clientId) throws SQLException {
        Client client = null;

        String sql = "SELECT * FROM client WHERE client_id = " + clientId;
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            String name = rs.getString("client_name");
            String surname = rs.getString("client_surname");
            String secondName = rs.getString("client_second_name");
            ClientType clientType = selectClientType(rs.getInt("client_type_id"));
            Account account = selectAccount(rs.getInt("account_id"));
            String email = rs.getString("client_email");
            String phone = rs.getString("client_phone");
            client = new Client(clientId, name, surname, secondName, clientType, account, email, phone);
        }

        rs.close();
        statement.close();
        return client;
    }

    public Client selectClient(Account account) throws SQLException {
        Client client = null;

        String sql = "SELECT * FROM client WHERE account_id = " + account.getId();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            int id = rs.getInt("client_id");
            String name = rs.getString("client_name");
            String surname = rs.getString("client_surname");
            String secondName = rs.getString("client_second_name");
            ClientType clientType = selectClientType(rs.getInt("client_type_id"));
            String email = rs.getString("client_email");
            String phone = rs.getString("client_phone");
            client = new Client(id, name, surname, secondName, clientType, account, email, phone);
        }

        rs.close();
        statement.close();
        return client;
    }

    public ArrayList<Client> selectClients() throws SQLException {
        ArrayList<Client> clients = new ArrayList<Client>();

        String sql = "SELECT * FROM client";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("client_id");
            String name = rs.getString("client_name");
            String surname = rs.getString("client_surname");
            String secondName = rs.getString("client_second_name");
            ClientType clientType = selectClientType(rs.getInt("client_type_id"));
            Account account = selectAccount(rs.getInt("account_id"));
            String email = rs.getString("client_email");
            String phone = rs.getString("client_phone");
            clients.add(new Client(id, name, surname, secondName, clientType, account, email, phone));
        }

        rs.close();
        statement.close();
        return clients;
    }

    // CLIENT TYPE FUNCTIONS

    public ClientType selectClientType(int clientTypeId) throws SQLException {
        ClientType clientType = null;

        String sql = "SELECT * FROM client_type WHERE client_type_id = " + clientTypeId;
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            String name = rs.getString("client_type_name");
            clientType = new ClientType(clientTypeId, name);
        }

        rs.close();
        statement.close();
        return clientType;
    }

    public ClientType selectClientType(String clientTypeName) throws SQLException {
        ClientType clientType = null;

        String sql = "SELECT * FROM client_type WHERE client_type_name = '" + clientTypeName + "'";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            int id = rs.getInt("client_type_id");
            clientType = new ClientType(id, clientTypeName);
        }

        rs.close();
        statement.close();
        return clientType;
    }

    public ArrayList<ClientType> selectClientTypes() throws SQLException {
        ArrayList<ClientType> clientTypes = new ArrayList<ClientType>();

        String sql = "SELECT * FROM client_type";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("client_type_id");
            String name = rs.getString("client_type_name");
            clientTypes.add(new ClientType(id, name));
        }

        rs.close();
        statement.close();
        return clientTypes;
    }

    // COUNTRY FUNCTIONS

    public void insertCountry(String name) throws SQLException {
        String sql = "INSERT INTO country(country_name) VALUES ('" + name + "')";
        executeStatement(sql);
        System.out.println("Country added");
    }

    public void updateCountryName(Country country, String newName) throws SQLException {
        String sql = "UPDATE country SET country_name = '" + newName + "' WHERE country_id = " + country.getId();
        executeStatement(sql);
        country.setName(newName);
        System.out.println("Country " + country.getId() + " name changed");
    }

    public void deleteCountry(Country country) throws SQLException {
        String sql = "DELETE FROM country WHERE country_id = " + country.getId();
        executeStatement(sql);
        System.out.println("Country " + country.getId() + " deleted");
    }

    public Country selectCountry(int countryId) throws SQLException {
        Country country = null;

        String sql = "SELECT * FROM country WHERE country_id = " + countryId;

        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            int id = rs.getInt("country_id");
            String name = rs.getString("country_name");
            country = new Country(id, name);
        }

        rs.close();
        statement.close();
        return country;
    }

    public ArrayList<Country> selectCountries() throws SQLException {
        ArrayList<Country> countries = new ArrayList<Country>();

        String sql = "SELECT * FROM country";

        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("country_id");
            String name = rs.getString("country_name");
            countries.add(new Country(id, name));
        }

        rs.close();
        statement.close();
        return countries;
    }

    // PLACE TYPE FUNCTIONS

    public void insertPlaceType(String name) throws SQLException {
        String sql = "INSERT INTO place_type(place_type_name) VALUES ('" + name + "')";
        executeStatement(sql);
        System.out.println("Place type added");
    }

    public void updatePlaceTypeName(PlaceType placeType, String newName) throws SQLException {
        String sql = "UPDATE place_type SET place_type_name = '" + newName + "' WHERE place_type_id = " + placeType.getId();
        executeStatement(sql);
        placeType.setName(newName);
        System.out.println("Place type " + placeType.getId() + " name changed");
    }

    public void deletePlaceType(PlaceType placeType) throws SQLException {
        String sql = "DELETE FROM place_type WHERE place_type_id = " + placeType.getId();
        executeStatement(sql);
        System.out.println("Place type " + placeType.getId() + " deleted");
    }

    public PlaceType selectPlaceType(int placeTypeId) throws SQLException {
        PlaceType placeType = null;

        String sql = "SELECT * FROM place_type WHERE place_type_id = " + placeTypeId;
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            int id = rs.getInt("place_type_id");
            String name = rs.getString("place_type_name");
            placeType = new PlaceType(id, name);
        }

        rs.close();
        statement.close();
        return placeType;
    }

    public ArrayList<PlaceType> selectPlaceTypes() throws SQLException {
        ArrayList<PlaceType> placeTypes = new ArrayList<PlaceType>();

        String sql = "SELECT * FROM place_type";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("place_type_id");
            String name = rs.getString("place_type_name");
            placeTypes.add(new PlaceType(id, name));
        }

        rs.close();
        statement.close();
        return placeTypes;
    }

    // REVIEW FUNCTIONS

    public void insertReview(String title, String body, Timestamp publicationTimestamp, Timestamp lastChangeTimestamp, int rating, Account account, Cleaning cleaning) throws SQLException {
        String sql = "INSERT INTO review(title, body, publication_timestamp, last_change_timestamp, rating, cleaning_id, account_id) VALUES ('" + title + "', '" + body + "', '" + publicationTimestamp.toString() + "', '" + lastChangeTimestamp + "', " + rating + ", " + cleaning.getId() + ", " + account.getId() + ")";
        executeStatement(sql);
        System.out.println("Review added");
    }

    public void updateReviewTitle(Review review, String newTitle) throws SQLException {
        String sql = "UPDATE review SET title = '" + newTitle + "' WHERE review_id = " + review.getId();
        executeStatement(sql);
        review.setTitle(newTitle);
        System.out.println("Review " + review.getId() + " title changed");
    }

    public void updateReviewBody(Review review, String newBody) throws SQLException {
        String sql = "UPDATE review SET body = '" + newBody + "' WHERE review_id = " + review.getId();
        executeStatement(sql);
        review.setBody(newBody);
        System.out.println("Review " + review.getId() + " body changed");
    }

    public void updateReviewPublicationTimestamp(Review review, Timestamp publicationTimestamp) throws SQLException {
        String sql = "UPDATE review SET publication_timestamp = '" + publicationTimestamp.toString() + "' WHERE review_id = " + review.getId();
        executeStatement(sql);
        review.setPublicationTimestamp(publicationTimestamp);
        System.out.println("Review " + review.getId() + " publication timestamp changed");
    }

    public void updateReviewLastChangeTimestamp(Review review, Timestamp lastChangeTimestamp) throws SQLException {
        String sql = "UPDATE review SET last_change_timestamp = '" + lastChangeTimestamp.toString() + "' WHERE review_id = " + review.getId();
        executeStatement(sql);
        review.setLastChangeTimestamp(lastChangeTimestamp);
        System.out.println("Review " + review.getId() + " last change timestamp changed");
    }

    public void updateReviewRating(Review review, int newRating) throws SQLException {
        String sql = "UPDATE review SET rating = " + newRating + " WHERE review_id = " + review.getId();
        executeStatement(sql);
        review.setRating(newRating);
        System.out.println("Review " + review.getId() + " rating changed");
    }

    public void updateReviewAccount(Review review, Account account) throws SQLException {
        String sql = "UPDATE review SET account_id = " + account.getId() + " WHERE review_id = " + review.getId();
        executeStatement(sql);
        review.setAccount(account);
        System.out.println("Review " + review.getId() + " account changed");
    }

    public void updateReviewCleaning(Review review, Cleaning cleaning) throws SQLException {
        String sql = "UPDATE review SET cleaning_id = " + cleaning.getId() + " WHERE review_id = " + review.getId();
        executeStatement(sql);
        review.setCleaning(cleaning);
        System.out.println("Review " + review.getId() + " cleaning changed");
    }

    public void deleteReview(Review review) throws SQLException {
        String sql = "DELETE FROM review WHERE review_id = " + review.getId();
        executeStatement(sql);
        System.out.println("Review " + review.getId() + " deleted");
    }

    public Review selectReview(int reviewId) throws SQLException {
        Review review = null;

        String sql = "SELECT * FROM review WHERE review_id = " + reviewId;
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            String title = rs.getString("title");
            String body = rs.getString("body");
            Timestamp publicationTimestamp = rs.getTimestamp("publication_timestamp");
            Timestamp lastChangeTimestamp = rs.getTimestamp("last_change_timestamp");
            int rating = rs.getInt("rating");
            Account account = selectAccount(rs.getInt("account_id"));
            Cleaning cleaning = selectCleaning(rs.getInt("cleaning_id"));
            review = new Review(reviewId, title, body, publicationTimestamp, lastChangeTimestamp, rating, account, cleaning);
        }

        rs.close();
        statement.close();
        return review;
    }

    public ArrayList<Review> selectReviews() throws SQLException {
        ArrayList<Review> reviews = new ArrayList<Review>();

        String sql = "SELECT * FROM review";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("review_id");
            String title = rs.getString("title");
            String body = rs.getString("body");
            Timestamp publicationTimestamp = rs.getTimestamp("publication_timestamp");
            Timestamp lastChangeTimestamp = rs.getTimestamp("last_change_timestamp");
            int rating = rs.getInt("rating");
            Account account = selectAccount(rs.getInt("account_id"));
            Cleaning cleaning = selectCleaning(rs.getInt("cleaning_id"));
            reviews.add(new Review(id, title, body, publicationTimestamp, lastChangeTimestamp, rating, account, cleaning));
        }

        rs.close();
        statement.close();
        return reviews;
    }

    // SERVICE FUNCTIONS

    public void insertService(String name, String description, double price) throws SQLException {
        String sql = "INSERT INTO service(service_name, service_description, service_price) VALUES ('" + name + "', '" + description + "', " + price + ")";
        executeStatement(sql);
        System.out.println("Service added");
    }

    public void updateServiceName(Service service, String newName) throws SQLException {
        String sql = "UPDATE service SET service_name = '" + newName + "' WHERE service_id = " + service.getId();
        executeStatement(sql);
        service.setName(newName);
        System.out.println("Service " + service.getId() + " name changed");
    }

    public void updateServiceDescription(Service service, String newDescription) throws SQLException {
        String sql = "UPDATE service SET service_description = '" + newDescription + "' WHERE service_id = " + service.getId();
        executeStatement(sql);
        service.setDescription(newDescription);
        System.out.println("Service " + service.getId() + " description changed");
    }

    public void updateServicePrice(Service service, double newPrice) throws SQLException {
        String sql = "UPDATE service SET service_price = " + newPrice + " WHERE service_id = " + service.getId();
        executeStatement(sql);
        service.setPrice(newPrice);
        System.out.println("Service " + service.getId() + " price changed");
    }

    public void deleteService(Service service) throws SQLException {
        String sql1 = "DELETE FROM cleaning_services WHERE service_id = " + service.getId();
        String sql2 = "DELETE FROM service WHERE service_id = " + service.getId();
        executeStatement(sql1);
        executeStatement(sql2);
        System.out.println("Service " + service.getId() + " deleted");
    }

    public Service selectService(int serviceId) throws SQLException {
        Service service = null;

        String sql = "SELECT * FROM service WHERE service_id = " + serviceId;
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            int id = rs.getInt("service_id");
            String name = rs.getString("service_name");
            String description = rs.getString("service_description");
            double price = rs.getDouble("service_price");
            service = new Service(id, name, description, price);
        }

        rs.close();
        statement.close();
        return service;
    }

    public ArrayList<Service> selectServices() throws SQLException {
        ArrayList<Service> services = new ArrayList<Service>();

        String sql = "SELECT * FROM service";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("service_id");
            String name = rs.getString("service_name");
            String description = rs.getString("service_description");
            double price = rs.getDouble("service_price");
            services.add(new Service(id, name, description, price));
        }

        rs.close();
        statement.close();
        return services;
    }

    public ArrayList<Service> selectServices(Cleaning cleaning) throws SQLException {
        ArrayList<Service> services = new ArrayList<Service>();

        String sql = "SELECT cleaner_id FROM cleaning_services WHERE cleaning_id = " + cleaning.getId();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next())
            services.add(selectService(rs.getInt(1)));

        rs.close();
        statement.close();
        return services;
    }

    // STREET FUNCTIONS

    public void insertStreet(String name, City city) throws SQLException {
        String sql = "INSERT INTO street(street_name, city_id) VALUES ('" + name + "', " + city.getId() + ")";
        executeStatement(sql);
        System.out.println("Street added");
    }

    public void updateStreetName(Street street, String newName) throws SQLException {
        String sql = "UPDATE street SET street_name = '" + newName + "' WHERE street_id = " + street.getId();
        executeStatement(sql);
        street.setName(newName);
        System.out.println("Street " + street.getId() + " name changed");
    }

    public void updateStreetCity(Street street, City city) throws SQLException {
        String sql = "UPDATE street SET city_id = " + city.getId() + " WHERE street_id = " + street.getId();
        executeStatement(sql);
        street.setCity(city);
        System.out.println("Street " + street.getId() + " city changed");
    }

    public void deleteStreet(Street street) throws SQLException {
        String sql = "DELETE FROM street WHERE street_id = " + street.getId();
        executeStatement(sql);
        System.out.println("Street " + street.getId() + " deleted");
    }

    public Street selectStreet(int streetId) throws SQLException {
        Street street = null;

        String sql = "SELECT * FROM street WHERE street_id = " + streetId;

        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            int id = rs.getInt("street_id");
            String name = rs.getString("street_name");
            City city = selectCity(rs.getInt("city_id"));
            street = new Street(id, city, name);
        }

        rs.close();
        statement.close();
        return street;
    }

    public ArrayList<Street> selectStreets() throws SQLException {
        ArrayList<Street> streets = new ArrayList<Street>();

        String sql = "SELECT * FROM street";

        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("street_id");
            String name = rs.getString("street_name");
            City city = selectCity(rs.getInt("city_id"));
            streets.add(new Street(id, city, name));
        }

        rs.close();
        statement.close();
        return streets;
    }

    public ArrayList<Street> selectStreets(City city) throws SQLException {
        ArrayList<Street> streets = new ArrayList<Street>();

        String sql = "SELECT * FROM street WHERE city_id = " + city.getId();

        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("street_id");
            String name = rs.getString("street_name");
            streets.add(new Street(id, city, name));
        }

        rs.close();
        statement.close();
        return streets;
    }
}
