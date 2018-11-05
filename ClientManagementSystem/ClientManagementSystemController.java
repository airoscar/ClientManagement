// ENSF 519-2 Java Project I
// Client Management System
// Oscar Chen & Savith Jayasekera
// November 5 2018

import java.sql.SQLException;

public class ClientManagementSystemController {

    private ClientManagementSystemData dataModel;
    private ClientManagementSystemView view;

    ClientManagementSystemController () {
        dataModel = new ClientManagementSystemData();
        view = new ClientManagementSystemView();

    }

    private void setUpView() {

    }

    private void setUpData() {
        try {
            dataModel.initializeDatabase();
        } catch (SQLException e) {

        }
    }


}
