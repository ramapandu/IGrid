package com.pg.webapp.counter_management;

import com.pg.webapp.SpreadsheetDemoUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.Window;

public class New_KPI_Window_View extends New_KPI_Window{
	
	private static final long serialVersionUID = -2162503814830257799L;
	private Window newKpiWindow;
	New_KPI_Window_2_View nkwv2;
	
	public New_KPI_Window_View() {
newKpiWindow=new Window("Counter Management-NEW KPI");
newKpiWindow.setHeight(700,Unit.PIXELS);
newKpiWindow.setWidth(1000, Unit.PIXELS);
newKpiWindow.addStyleName("settings");
newKpiWindow.setContent(new_Kpi_Container);
newKpiWindow.setModal(true);
buildTabSheet();
buildGridButtons();
	}

	private void buildGridButtons() {
//		new_kpi_gridLayout1.addComponent(component, column, row);
	}

	private void buildTabSheet() {
		new_kpi_tabsheet.addSelectedTabChangeListener(new SelectedTabChangeListener() {

				private static final long serialVersionUID = -1568161407214112841L;

			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				com.vaadin.ui.JavaScript
						.eval("setTimeout(function(){prettyPrint();},300);");
			}
		});
		
		create_kpi_btn1.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 8008951408085779254L;

			@Override
			public void buttonClick(ClickEvent event) {
				nkwv2=new New_KPI_Window_2_View();
				
				
				getAppUI().getUI().addWindow(nkwv2.getNewKpiWindow2());
			}
		});
		
		create_kpi_btn2.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 8812568271742255387L;

			@Override
			public void buttonClick(ClickEvent event) {
				
			}
		});
		
		create_kpi_btn3.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -8758507220017352677L;

			@Override
			public void buttonClick(ClickEvent event) {
				
			}
		});
		
		create_kpi_btn4.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 7909759170470719983L;

			@Override
			public void buttonClick(ClickEvent event) {
				
			}
		});
		
		create_kpi_btn5.addClickListener(new Button.ClickListener() {

			
			private static final long serialVersionUID = -1083591838761762511L;

			@Override
			public void buttonClick(ClickEvent event) {
				
			}
		});
		
	}

	public Window getNewKpiWindow() {
		newKpiWindow.center();
		return newKpiWindow;
	}

	SpreadsheetDemoUI getAppUI() {
		return (SpreadsheetDemoUI) UI.getCurrent();
	}

	public void createButtons() {
		Label kpi_label=new Label("KPI1");
		Button update_Button,submit_Button,delete_Button;
		update_Button=new Button("Update");
		submit_Button=new Button("Sumit");
		delete_Button=new Button("Delete");
		
		
		update_Button.addClickListener(new ClickListener() {
		private static final long serialVersionUID = 1937340336318912863L;
			@Override
			public void buttonClick(ClickEvent event) {

			}
		});

		submit_Button.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -4770467594962004515L;

			@Override
			public void buttonClick(ClickEvent event) {

			}
		});
		

		delete_Button.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -6728816866679274968L;

			@Override
			public void buttonClick(ClickEvent event) {

			}
		});
		
		int gridSize=0;
		gridSize=new_kpi_gridLayout1.getRows();
		System.out.println("Grid size: "+gridSize);
		for(int index=1;index<=15;index++){
//		if(nkwv.new_kpi_gridLayout1.getComponent(0, index)!=null){
//			nkwv.new_kpi_gridLayout1.get
        new_kpi_gridLayout1.addComponent(kpi_label, 0, index);
        new_kpi_gridLayout1.addComponent(update_Button, 1, index);
        new_kpi_gridLayout1.addComponent(submit_Button, 2, index);
        new_kpi_gridLayout1.addComponent(delete_Button, 3, index);
        break;
//		}
		}  		
	}
	
	
}
