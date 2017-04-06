package com.pg.webapp.counter_management;

import java.sql.SQLException;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.pg.webapp.SpreadsheetDemoUI;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

public class CounterManagementView extends CounterManagement {
	
	
	private static final long serialVersionUID = -8511220893016266583L;
	
	private Window counterManagementWindow;
	KpiListWindowView klv;
	New_KPI_Window_View nkv;

	public CounterManagementView() {
		
		counterManagementWindow=new Window("Counter Management");
		counterManagementWindow.setHeight(500,Unit.PIXELS);
		counterManagementWindow.setWidth(800, Unit.PIXELS);
		counterManagementWindow.addStyleName("settings");
		counterManagementWindow.setContent(counterMangementContainer);
		counterManagementWindow.setModal(true);
		radioButtonsGroup.setImmediate(true);
	}


public Window getCounterMgmtWindow(){
	counterManagementWindow.center();
	kpiList.addClickListener(new Button.ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			klv=new KpiListWindowView();
			getAppUI().getUI().addWindow(klv.getKpiSelectionWindow());
		}
	});
	
	newKpi.addClickListener(new Button.ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			try {
				nkv=new New_KPI_Window_View();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			getAppUI().getUI().addWindow(nkv.getNewKpiWindow());
		}
	});
	return counterManagementWindow;
}

public KpiListWindowView getKpiWindowInstance(){
	return klv;
}

SpreadsheetDemoUI getAppUI() {
	return (SpreadsheetDemoUI) UI.getCurrent();
}

}
	
