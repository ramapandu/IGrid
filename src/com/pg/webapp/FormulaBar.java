package com.pg.webapp;
import com.pg.webapp.database.DbConnection;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class FormulaBar extends BrowseFormula {
	
	private static final long serialVersionUID = 1344900860449839957L;
	MenuItem gridMenu;
	MenuItem browseMenu;
	
	private void FormulaBar(){
		
	
	}
	
	public MenuBar getMenuBar(){
		getGridMenuBar();
	    getFormulaBar();
		return browseFormula;
	}

	private void getGridMenuBar() {
		 gridMenu = browseFormula.addItem("FILE", null, null);
		 
		Command newGridCommand = new Command() {
            private static final long serialVersionUID = 4483012525105015694L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				Notification.show("select--"+selectedItem.getId()+"--");
				
				getAppUI().getSpreadsheet_dao().getSpreadsheet().createNewSheet("new", 100, 30);
//				Spreadsheet s=new Spreadsheet();
//				s.createNewSheet("new", 100, 30);
			}
        };
        Command openGridCommand = new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				Notification.show("select--"+selectedItem.getId()+"--");
				
			}
        };
        Command saveGridCommand = new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				Notification.show("select--"+selectedItem.getId()+"--");
			}
        };
        Command closeGridCommand = new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				Notification.show("select--"+selectedItem.getId()+"--");
//				getAppUI().getSpreadsheet_dao().getSpreadsheet().
			}
        };
        Command refreshGridCommand = new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
//				Notification.show("select--"+selectedItem.getId()+"--");
				getAppUI().getSpreadsheet_dao().getSpreadsheet().refreshAllCellValues();
			}
        };
        
        Command renameGridCommand = new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				final Window subWindow = new Window("Rename Grid");
				VerticalLayout popupContent = new VerticalLayout();
				final TextField newGridName=new TextField("New Grid Name");
				popupContent.addComponent(newGridName);
				Button submitButton=new Button("Submit");
				submitButton.addClickListener(new ClickListener() {
					private static final long serialVersionUID = 7989433934407716083L;

					@Override
					public void buttonClick(ClickEvent event) {
						getAppUI().getSpreadsheet_dao().setGridName(newGridName.getValue());
//						getAppUI().getSpreadsheet_dao().getSpreadsheet().setCaption(newGridName.getValue());
						getAppUI().getSpreadsheet_dao().getSpreadsheet().setSheetName(0, newGridName.getValue());
//						getAppUI().getUI();
//						UI.getCurrent().getPage().reload();
						subWindow.close();
					}
				});
				Button cancelButton=new Button("Cancel");
				cancelButton.addClickListener(new ClickListener() {

					private static final long serialVersionUID = -2390118376572347330L;

					@Override
					public void buttonClick(ClickEvent event) {
						subWindow.close();
					}
				});
				popupContent.addComponent(submitButton);

//				PopupView popup = new PopupView("Rename Grid", popupContent);
				subWindow.setContent(popupContent);
				subWindow.addStyleName("renamegridwindow");
				subWindow.center();
				getAppUI().getUI().addWindow(subWindow);
				
			}
        };
        
        Command insertNewColumnCommand = new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				Notification.show("select--"+selectedItem.getId()+"--");
		
				
				
				try{
					DbConnection db=new DbConnection();
			   	    java.sql.Statement st = db.getConnection().createStatement();
			    int n = st.executeUpdate("ALTER TABLE mytable ADD col int");
			    System.out.println("Query OK, " + n + " rows affected");
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
        };
        
        MenuItem new1 = gridMenu.addItem("New Grid", newGridCommand);
//        new1.addSeparator();
        MenuItem open1 = gridMenu.addItem("Open Grid", openGridCommand);
//        open1.addSeparator();
        MenuItem insert1 = gridMenu.addItem("Insert Column", insertNewColumnCommand);
//      refresh1.addSeparator();
        MenuItem save1 = gridMenu.addItem("Save Grid", saveGridCommand);
//        save1.addSeparator();
        MenuItem rename1 = gridMenu.addItem("Rename Grid", renameGridCommand);
//      refresh1.addSeparator();
        MenuItem close1 = gridMenu.addItem("Close Grid", closeGridCommand);
//        close1.addSeparator();
        MenuItem refresh1 = gridMenu.addItem("Refresh", refreshGridCommand);
//        refresh1.addSeparator();
//        MenuItem delete1=gridMenu.addItem("", )
      
	}


	MenuBar getFormulaBar(){
		Command mycommand = new Command() {
            private static final long serialVersionUID = 4483012525105015694L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				Notification.show("select--"+selectedItem.getId()+"--");
			}
        };
	
        browseMenu = browseFormula.addItem("Browse", null, null);

     // Submenu item with a sub-submenu
     MenuItem ran = browseMenu.addItem("RAN", null, null);
     ran.addItem("ZTE",
         mycommand);
     ran.addItem("Huawei",
         mycommand);
     ran.addItem("Ericsson",
	        mycommand);
     
     MenuItem core = browseMenu.addItem("Core", null, null);
     core.addItem("Huawei",
          mycommand);
     
     MenuItem vas = browseMenu.addItem("VAS", null, null);
     vas.addItem("Huawei",
          mycommand);
     
     MenuItem in = browseMenu.addItem("IN", null, null);
     in.addItem("Ericsson",
           mycommand);
     
     MenuItem transmission = browseMenu.addItem("Transmission", null, null);
     transmission.addItem("SPO",
           mycommand);
     transmission.addItem("WDM",
        mycommand);
     transmission.addItem("MSTP",
	         mycommand);
		
		return browseFormula;
	}
	SpreadsheetDemoUI getAppUI() {
		return (SpreadsheetDemoUI) UI.getCurrent();
	}
	
	
}