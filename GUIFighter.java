package misc.scripts.fighter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.text.NumberFormatter;

import misc.scripts.fighter.util.Monster;
import misc.scripts.fighter.util.Util;

import org.hexbot.api.wrapper.interactable.Npc;


public class GUIFighter extends JFrame{



	public static final int WIDTH = 300;
	public static final int HEIGHT = 300;

	public GUIFighter(){
		setSize(WIDTH, HEIGHT);
		setTitle("MiscDragons");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		createContents();
		setVisible(true);
	}

	JTabbedPane pane;

	JPanel pnlOptions;
	JPanel pnlNpcs;
	JPanel pnlLoadedNpcs;
	JPanel pnlSelectedNpcs;
	JPanel pnlLoot;
	JPanel pnlAddLoot;
	JPanel pnlSouth;

	DefaultListModel<Monster> listModelLoadedNpcs;
	JList<Monster> listLoadedNpcs;
	DefaultListModel<Monster> listModelSelectedNpcs;
	JList<Monster> listSelectedNpcs;
	DefaultListModel<String> listModelLoot;
	JList<String> listLoot;

	JScrollPane scrollLoadedNpcs;
	JScrollPane scrollSelectedNpcs;
	JScrollPane scrollLoot;

	JSlider sliderEatPercent;
	
	JButton btnAddNpc;
	JButton btnRefresh;
	JButton btnRemoveNpc;
	JButton btnAddLoot;
	JButton btnRemoveLoot;
	JButton btnStart;

	JTextField txtAddLootId;
	JTextField txtFoodId;


	private void createContents(){
		this.setLayout(new BorderLayout());
		pane = new JTabbedPane();
		pane.setTabPlacement(JTabbedPane.TOP);
		MyActionListener listener = new MyActionListener();
		//		NumberFormat intFormat = NumberFormat.getIntegerInstance();
		//		intFormat.setGroupingUsed(false);
		//		NumberFormatter numberFormatter = new NumberFormatter(intFormat);
		//		numberFormatter.setValueClass(Integer.class);
		//		numberFormatter.setAllowsInvalid(false);
		//		numberFormatter.setMinimum(0);


		pnlOptions = new JPanel();
		pnlOptions.setLayout(new FlowLayout());
		//		pnlOptions.add(txtFoodName);
		txtFoodId = new JTextField();
		txtFoodId.setColumns(10);
		txtFoodId.setPreferredSize(new Dimension(80,20));
		sliderEatPercent = new JSlider(0, 100);
		sliderEatPercent.setMajorTickSpacing(25);
		sliderEatPercent.setMinorTickSpacing(5);
		sliderEatPercent.setPaintTicks(true);
		sliderEatPercent.setToolTipText("Percent of health to eat at.");
		pnlOptions.add(new JLabel("Food ID:"));
		pnlOptions.add(txtFoodId);
		pnlOptions.add(new JLabel("Percent of health to eat at:"));
		pnlOptions.add(sliderEatPercent);
		pnlOptions.revalidate();
		//this.add(pnlOptions, BorderLayout.NORTH);
		pane.add("Options", pnlOptions);


		//load NPCs interface 
		pnlNpcs = new JPanel();
		pnlNpcs.setLayout(new BorderLayout());

		pnlLoadedNpcs = new JPanel();
		pnlLoadedNpcs.setLayout(new BorderLayout());
		pnlLoadedNpcs.setPreferredSize(new Dimension(150, 200));
		listModelLoadedNpcs = new DefaultListModel<Monster>();
		listLoadedNpcs = new JList<Monster>(listModelLoadedNpcs);
		scrollLoadedNpcs = new JScrollPane();
		scrollLoadedNpcs.getViewport().add(listLoadedNpcs);
		btnRefresh = new JButton("Load Npcs");
		btnRefresh.addActionListener(listener);
		btnAddNpc = new JButton("Add >>");
		btnAddNpc.addActionListener(listener);
		//btnAddNpc.setEnabled(false); //enable /diable based on if an item is selected
		pnlLoadedNpcs.add(btnRefresh, BorderLayout.NORTH);
		pnlLoadedNpcs.add(scrollLoadedNpcs, BorderLayout.CENTER);
		pnlLoadedNpcs.add(btnAddNpc, BorderLayout.SOUTH);

		//select NPCs interface
		pnlSelectedNpcs = new JPanel();
		pnlSelectedNpcs.setLayout(new BorderLayout());
		pnlSelectedNpcs.setPreferredSize(new Dimension(150, 200));
		listModelSelectedNpcs = new DefaultListModel<Monster>();
		listSelectedNpcs = new JList<Monster>(listModelSelectedNpcs);
		scrollSelectedNpcs = new JScrollPane();
		scrollSelectedNpcs.getViewport().add(listSelectedNpcs);
		btnRemoveNpc = new JButton("Remove selected");
		btnRemoveNpc.addActionListener(listener);
		JLabel lblNpcsToFight = new JLabel("Npcs to fight:");
		lblNpcsToFight.setHorizontalAlignment(SwingConstants.CENTER);
		pnlSelectedNpcs.add(lblNpcsToFight, BorderLayout.NORTH);
		pnlSelectedNpcs.add(scrollSelectedNpcs, BorderLayout.CENTER);
		pnlSelectedNpcs.add(btnRemoveNpc, BorderLayout.SOUTH);

		pnlNpcs.add(pnlLoadedNpcs, BorderLayout.CENTER);
		pnlNpcs.add(pnlSelectedNpcs, BorderLayout.EAST);
		pnlLoadedNpcs.revalidate();
		pnlSelectedNpcs.revalidate();
		pnlNpcs.revalidate();
		//this.add(pnlNpcs, BorderLayout.CENTER);
		pane.add("Npcs",pnlNpcs);


		//loot input interface
		pnlLoot = new JPanel();
		pnlLoot.setBackground(Color.green);
		pnlLoot.setLayout(new BorderLayout());
		//pnlLoot.setPreferredSize(new Dimension(150, 200));
		listModelLoot = new DefaultListModel<String>();
		listLoot = new JList<String>(listModelLoot);
		scrollLoot = new JScrollPane();
		scrollLoot.getViewport().add(listLoot);
		scrollLoot.setBackground(Color.green);
		pnlAddLoot = new JPanel();
		//pnlAddLoot.setPreferredSize(new Dimension(150,60));
		btnAddLoot = new JButton("Add");
		btnAddLoot.addActionListener(listener);
		txtAddLootId = new JTextField();
		txtAddLootId.setColumns(6);
		pnlAddLoot.add(new JLabel("Add item ID or name"));
		pnlAddLoot.add(txtAddLootId);
		pnlAddLoot.add(btnAddLoot);
		btnRemoveLoot = new JButton("Remove selected");
		btnRemoveLoot.setSize(new Dimension(80,30));
		btnRemoveLoot.addActionListener(listener);
		pnlLoot.add(pnlAddLoot, BorderLayout.NORTH);
		pnlLoot.add(scrollLoot, BorderLayout.CENTER);
		pnlLoot.add(btnRemoveLoot, BorderLayout.SOUTH);
		//this.add(pnlLoot, BorderLayout.WEST);
		pnlAddLoot.revalidate();
		pnlLoot.revalidate();
		pane.add("Loot",pnlLoot);

		this.add(pane, BorderLayout.CENTER);

		pnlSouth = new JPanel();
		btnStart = new JButton("Start");
		btnStart.addActionListener(listener);
		pnlSouth.add(btnStart);

		this.add(pnlSouth, BorderLayout.SOUTH);
		
		txtFoodId.setText("379");
		
	}

	private class MyActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnAddNpc){
				if(listLoadedNpcs.getSelectedIndex() < 0)
					return;
				DefaultListModel<Monster> listModel = (DefaultListModel<Monster>) listSelectedNpcs.getModel();
				Monster selectedValue = listLoadedNpcs.getSelectedValue();
				if(!listModel.contains(selectedValue))
					listModel.addElement(selectedValue);
				listSelectedNpcs.setModel(listModel);
			}
			if(e.getSource() == btnRemoveNpc){
				if(listSelectedNpcs.getSelectedIndex() < 0)
					return;
				DefaultListModel<Monster> listModel = (DefaultListModel<Monster>) listSelectedNpcs.getModel();
				listModel.remove(listSelectedNpcs.getSelectedIndex());
				listSelectedNpcs.setModel(listModel);
			}
			if(e.getSource() == btnRefresh){
				DefaultListModel<Monster> newListModel = new DefaultListModel<Monster>();
				for(Monster monster : Util.getAttackableMonsters()){
					if(!newListModel.contains(monster))
						newListModel.addElement(monster);
				}
				listLoadedNpcs.setModel(newListModel);
			}
			if(e.getSource() == btnAddLoot){
				DefaultListModel<String> listModel =  (DefaultListModel<String>) listLoot.getModel();
				String value = txtAddLootId.getText();
				if(value == null || value.isEmpty() || listModel.contains(value))
					return;
				listModel.addElement(value);
				listLoot.setModel(listModel);
				txtAddLootId.setText("");
			}
			if(e.getSource() == btnRemoveLoot){
				if(listLoot.getSelectedIndex() < 0)
					return;
				DefaultListModel<String> listModel =  (DefaultListModel<String>) listLoot.getModel();
				listModel.remove(listLoot.getSelectedIndex());
				listLoot.setModel(listModel);
			}
			if(e.getSource() == btnStart){
				int idFood = -1;
				List<Integer> lootIds = new ArrayList<Integer>();
				List<String> lootNames = new ArrayList<String>();
				List<Monster> monsters = new ArrayList<Monster>();

				try{
					idFood = Integer.parseInt(txtFoodId.getText());
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null,"Invalid food ID!","Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Object[] lootArray = ((DefaultListModel<String>) listLoot.getModel()).toArray();
				for(Object o : lootArray){
					String s = (String) o;
					//if value is a number, assume it is an ID, otherwise it is a name
					try{
						int id = Integer.parseInt(s);
						lootIds.add(id);
					}catch(Exception ex){
						lootNames.add(s);
					}
				}
				MiscFighter.lootIds = lootIds;
				MiscFighter.lootNames = lootNames;

				for(Object o : ((DefaultListModel<Monster>) listSelectedNpcs.getModel()).toArray()){{
					Monster m = (Monster) o;
					monsters.add(m);
				}
				MiscFighter.monsters = monsters;

				if(listSelectedNpcs.getModel().getSize() < 1){
					JOptionPane.showMessageDialog(null,"No npcs selected!","Error",JOptionPane.ERROR_MESSAGE);
				}

				MiscFighter.idFood = idFood;
				MiscFighter.percentHealthToEat = sliderEatPercent.getValue();
				
				GUIFighter.this.setVisible(false);
				GUIFighter.this.dispose();
				}
			}

		}
	}
}

