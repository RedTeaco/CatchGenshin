package org.redtea.GUI;

import com.formdev.flatlaf.FlatLightLaf;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.redtea.GUI.Controller.label;
import org.redtea.Genshin.ItemEntity;
import org.redtea.Genshin.addLogs;
import org.redtea.Genshin.miHoYoSoup;
import org.redtea.Threads.WorkThread;
import org.redtea.Utils.Utils;
import org.redtea.resourceUpdate.Main;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class MainWindow {
    private static List<ItemEntity> itemEntityList;
    private JPanel root;
    private JTabbedPane MainTab;
    private JTabbedPane SecondFromMainTeb;
    private JPanel details;
    private JScrollPane detailsScroll;
    private JPanel detailsInScroll;
    private JPanel general;
    private JPanel Search;
    private JPanel AddLogs;
    private JButton StartButton;
    private JTextField uidTextField;
    private JTextField nameTextField;
    private JComboBox<String> gachaTypeBox;
    private JComboBox<String> rankeBox;
    private JTextField CountTextField;
    private JButton SubmitButton;
    private JLabel uidlabel;
    private JComboBox<String> ItemType;
    private JProgressBar totalNum;
    private JProgressBar weaponNum;
    private JProgressBar characterNum;
    private JProgressBar fifthStarNum;
    private JProgressBar forthStarNum;
    private JProgressBar normalPool;
    private JProgressBar UPPool;
    private JProgressBar weaponPool;
    private JButton resourcesUpdateButton;

    private JProgressBar StartProgress;
    private int[] ints;
    private List<ItemEntity> fifthStarList;

    private int total;
    private int weapon;
    private int character;
    private int fifth;
    private int fourth;

    private static boolean GetURL;

    private Thread workThread = null;

    //获取mihoyosoup中获取到的结果（五星）

    //事件监听器
    public MainWindow() {
        StartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    StartButton.setEnabled(false);
                    if (workThread == null) {
                        workThread = new WorkThread(StartProgress);
                        workThread.start();
                    }

                    miHoYoSoup miHoYoSoup = new miHoYoSoup();
                    miHoYoSoup.miHoYoSoupGetLogs(GetURL);
                    if (GetURL) {
                        miHoYoSoup.miHoYoSoupGetLogs(true);
                    }
                    setInts(miHoYoSoup.getResultIntList());
                    String uid;
                    try {
                        uid = miHoYoSoup.getResultList().get(0).getUid();
                    } catch (
                            NullPointerException e1) {
                        workThread.interrupt();
                        StartButton.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "当前url已过期，请点击游戏中的历史记录后重试...", "出现错误!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    try {
                        addLogs addLogs = new addLogs(uid);
                        List<ItemEntity> list = addLogs.getLogData();
                        setFifthStarList(miHoYoSoup.get_collects(list, miHoYoSoup.getResultList()));
                    } catch (
                            Exception e1) {
                        setFifthStarList(miHoYoSoup.getResultList());
                    }

                    fifth = getInts()[5];
                    fourth = getInts()[4];
                    total = getInts()[0];
                    character = getInts()[1];
                    weapon = getInts()[2];

                    addLabelDetails(getFifthStarList());

                    totalNum.setValue(100);
                    totalNum.setString(String.valueOf(total));
                    totalNum.setStringPainted(true);

                    int weaponNumValue = (int) ((double) weapon / total * 100);
                    weaponNum.setValue(weaponNumValue);
                    weaponNum.setString(String.valueOf(weapon));
                    weaponNum.setStringPainted(true);

                    int characterNumValue = (int) ((double) character / total * 100);
                    characterNum.setValue(characterNumValue);
                    characterNum.setString(String.valueOf(character));
                    characterNum.setStringPainted(true);

                    int fifthStarNumValue = (int) ((double) fifth / total * 100);
                    fifthStarNum.setValue(fifthStarNumValue);
                    fifthStarNum.setString(String.valueOf(fifth));
                    fifthStarNum.setStringPainted(true);

                    int forthStarNumValue = (int) ((double) fourth / total * 100);
                    forthStarNum.setValue(forthStarNumValue);
                    forthStarNum.setString(String.valueOf(fourth));
                    forthStarNum.setStringPainted(true);

                    String normalPoolNum = miHoYoSoup.getCounts().get(1);
                    int normalPoolValue = Integer.parseInt(normalPoolNum);
                    normalPool.setValue(normalPoolValue);
                    normalPool.setString(normalPoolNum);
                    normalPool.setStringPainted(true);

                    String UPPoolNum = miHoYoSoup.getCounts().get(2);
                    int characterPoolValue = Integer.parseInt(UPPoolNum);
                    UPPool.setValue(characterPoolValue);
                    UPPool.setString(UPPoolNum);
                    UPPool.setStringPainted(true);

                    String weaponPoolNum = miHoYoSoup.getCounts().get(3);
                    int weaponPoolValue = Integer.parseInt(weaponPoolNum);
                    weaponPool.setValue(weaponPoolValue);
                    weaponPool.setString(weaponPoolNum);
                    weaponPool.setStringPainted(true);

                    details.repaint();
                    org.redtea.Genshin.miHoYoSoup.setCount(100);
                    general.repaint();
                    StartButton.setEnabled(true);
                }).start();
            }
        });

        SubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String uid;
                if (uidTextField.getText().equals("")) {
                    uid = getFifthStarList().get(0).getUid();
                } else {
                    uid = uidTextField.getText();
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String time = simpleDateFormat.format(System.currentTimeMillis());
                String rank_type = "";
                String gacha_type = "";
                String rankBoxValue = (String) rankeBox.getSelectedItem();
                String gachaTypeBoxValue = (String) gachaTypeBox.getSelectedItem();
                assert rankBoxValue != null;
                if (rankBoxValue.equals("金色")) {
                    rank_type = "5";
                } else if (rankBoxValue.equals("紫色")) {
                    rank_type = "4";
                }
                assert gachaTypeBoxValue != null;
                switch (gachaTypeBoxValue) {
                    case "常驻池" -> gacha_type = "200";
                    case "角色池" -> gacha_type = "301";
                    case "武器池" -> gacha_type = "302";
                }
                ItemEntity itemEntity = new ItemEntity(uid, "", (String) ItemType.getSelectedItem(),
                        CountTextField.getText(),
                        nameTextField.getText(),
                        gacha_type, time, "", "zh-cn", rank_type);
                List<ItemEntity> ls = new ArrayList<>();
                ls.add(itemEntity);
                setItemEntityList(ls);
                addLogs addLogs = new addLogs(itemEntity);
                addLogs.mainMethod();
            }
        });

        resourcesUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.start();
            }
        });
    }


    public static List<ItemEntity> getItemEntityList() {
        return itemEntityList;
    }

    public static void setItemEntityList(List<ItemEntity> itemEntityList) {
        MainWindow.itemEntityList = itemEntityList;
    }

    public static void main(String[] args) {
        FlatLightLaf.install();
        String path = new File("").getAbsolutePath() + "\\src\\main\\resources\\fonts\\Genshin.ttf";
        try {
            Utils.InitGlobalFont(label.getSelfDefinedFont(path, 16));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        MainWindow mainWindow = new MainWindow();
        Font ButtonFont = mainWindow.StartButton.getFont().deriveFont(Font.PLAIN, 20);
        mainWindow.StartButton.setFont(ButtonFont);
        mainWindow.SubmitButton.setFont(ButtonFont);
        mainWindow.resourcesUpdateButton.setFont(ButtonFont);
        mainWindow.setUI();
    }



    public int[] getInts() {
        return ints;
    }

    public void setInts(int[] ints) {
        this.ints = ints;
    }

    public List<ItemEntity> getFifthStarList() {
        return fifthStarList;
    }

    public void setFifthStarList(List<ItemEntity> fifthStarList) {
        this.fifthStarList = fifthStarList;
    }

    public static boolean isGetURL() {
        return GetURL;
    }

    public static void setGetURL(boolean getURL) {
        GetURL = getURL;
    }

    private void setUI() {
        String path;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point p = ge.getCenterPoint();
        JFrame frame = new JFrame("原神抽卡记录");
        detailsScroll = new JScrollPane();
        detailsScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        detailsInScroll = new JPanel(new GridBagLayout());

        detailsInScroll.setOpaque(false);
        SecondFromMainTeb.addTab("总  览", general);
        SecondFromMainTeb.addTab("详  情", details);
        details.setVisible(true);
        detailsScroll.setOpaque(false);
        detailsScroll.getViewport().setOpaque(false);
        //设置details的布局管理器
        details.setLayout(new GridLayout());
        details.add(detailsScroll);//, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 1, false));
        detailsScroll.setVisible(true);
        detailsScroll.setViewportView(detailsInScroll);
        detailsScroll.getVerticalScrollBar().setUnitIncrement(16);
        //设置总览的布局管理器等
        general.setOpaque(true);
        general.setVisible(true);

        frame.setContentPane(root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        //设置界面在显示器中央显示
        frame.setLocation((p.x - frame.getWidth() / 2), (p.y - frame.getHeight() / 2));
        frame.setVisible(true);
    }

    private void addLabelDetails(List<ItemEntity> itemEntityList) {
        int i = 0;
        for (ItemEntity list : itemEntityList) {
            //布局管理器
            GridBagConstraints gbcProgressBar = new GridBagConstraints(1, i, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
            GridBagConstraints gbcLabel = new GridBagConstraints(0, i, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);

            String type = "";
            if ("武器".equals(list.getItem_type())) {
                type = "weapons";
            } else if ("角色".equals(list.getItem_type())) {
                type = "characters";
            }
            label label = new label(list.getName() + " " + list.getGacha_type(), type + "\\" + list.getName() + ".png");
            JProgressBar progressBar = new JProgressBar(0, 90);
            progressBar.setBorderPainted(false);
            progressBar.setPreferredSize(new Dimension(300, 20));
            progressBar.setValue(Integer.parseInt(list.getCount()));
            progressBar.setForeground(Utils.getColor(Double.parseDouble(list.getCount())));
            progressBar.setStringPainted(true);
            progressBar.setString(list.getCount());
            detailsInScroll.add(label, gbcLabel);
            detailsInScroll.add(progressBar, gbcProgressBar);
            i++;
        }
    }



    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        root = new JPanel();
        root.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        root.setOpaque(true);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setDoubleBuffered(true);
        panel1.setOpaque(false);
        root.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        MainTab = new JTabbedPane();
        MainTab.setAutoscrolls(false);
        Font MainTabFont = this.$$$getFont$$$(null, -1, -1, MainTab.getFont());
        if (MainTabFont != null) MainTab.setFont(MainTabFont);
        MainTab.setInheritsPopupMenu(false);
        MainTab.setOpaque(false);
        MainTab.setTabPlacement(2);
        panel1.add(MainTab, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(950, 650), null, 0, false));
        Search = new JPanel();
        Search.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        Search.setOpaque(false);
        Search.setPreferredSize(new Dimension(-1, -1));
        MainTab.addTab("抽卡查询", Search);
        SecondFromMainTeb = new JTabbedPane();
        SecondFromMainTeb.setAutoscrolls(false);
        Font SecondFromMainTebFont = this.$$$getFont$$$(null, -1, -1, SecondFromMainTeb.getFont());
        if (SecondFromMainTebFont != null) SecondFromMainTeb.setFont(SecondFromMainTebFont);
        SecondFromMainTeb.setOpaque(false);
        SecondFromMainTeb.setTabPlacement(2);
        SecondFromMainTeb.setVisible(true);
        Search.add(SecondFromMainTeb, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(459, 543), null, 0, false));
        general = new JPanel();
        general.setLayout(new GridLayoutManager(8, 3, new Insets(0, 0, 0, 0), -1, -1));
        SecondFromMainTeb.addTab("总览", general);
        final JLabel label1 = new JLabel();
        label1.setText("抽卡总数");
        general.add(label1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        totalNum = new JProgressBar();
        totalNum.setBorderPainted(false);
        totalNum.setStringPainted(false);
        totalNum.setValue(0);
        general.add(totalNum, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(300, 20), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("武器数");
        general.add(label2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        weaponNum = new JProgressBar();
        weaponNum.setBorderPainted(false);
        general.add(weaponNum, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(300, 20), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("角色数");
        general.add(label3, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("五星数");
        general.add(label4, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("四星数");
        general.add(label5, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("常驻池已垫");
        general.add(label6, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("角色池已垫");
        general.add(label7, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("武器池已垫");
        general.add(label8, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        characterNum = new JProgressBar();
        characterNum.setBorderPainted(false);
        general.add(characterNum, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(300, 20), null, 0, false));
        fifthStarNum = new JProgressBar();
        fifthStarNum.setBorderPainted(false);
        general.add(fifthStarNum, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(300, 20), null, 0, false));
        forthStarNum = new JProgressBar();
        forthStarNum.setBorderPainted(false);
        general.add(forthStarNum, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(300, 20), null, 0, false));
        normalPool = new JProgressBar();
        normalPool.setBorderPainted(false);
        normalPool.setMaximum(90);
        general.add(normalPool, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(300, 20), null, 0, false));
        UPPool = new JProgressBar();
        UPPool.setBorderPainted(false);
        UPPool.setMaximum(90);
        general.add(UPPool, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(300, 20), null, 0, false));
        weaponPool = new JProgressBar();
        weaponPool.setBorderPainted(false);
        weaponPool.setMaximum(90);
        general.add(weaponPool, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(300, 20), null, 0, false));
        final Spacer spacer1 = new Spacer();
        general.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, new Dimension(20, -1), null, 0, false));
        details = new JPanel();
        details.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        SecondFromMainTeb.addTab("详情", details);
        StartButton = new JButton();
        StartButton.setAutoscrolls(false);
        Font StartButtonFont = this.$$$getFont$$$(null, -1, -1, StartButton.getFont());
        if (StartButtonFont != null) StartButton.setFont(StartButtonFont);
        StartButton.setHorizontalTextPosition(11);
        StartButton.setText("开始查询");
        Search.add(StartButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        resourcesUpdateButton = new JButton();
        Font resourcesUpdateButtonFont = this.$$$getFont$$$(null, -1, -1, resourcesUpdateButton.getFont());
        if (resourcesUpdateButtonFont != null) resourcesUpdateButton.setFont(resourcesUpdateButtonFont);
        resourcesUpdateButton.setText("资源更新");
        Search.add(resourcesUpdateButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        StartProgress = new JProgressBar();
        StartProgress.setBorderPainted(false);
        StartProgress.setFocusable(false);
        StartProgress.setMaximum(100);
        Search.add(StartProgress, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        AddLogs = new JPanel();
        AddLogs.setLayout(new GridLayoutManager(7, 4, new Insets(0, 0, 0, 0), -1, -1));
        AddLogs.setOpaque(false);
        MainTab.addTab("添加记录", AddLogs);
        final Spacer spacer2 = new Spacer();
        AddLogs.add(spacer2, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        uidTextField = new JTextField();
        uidTextField.setText("");
        AddLogs.add(uidTextField, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        nameTextField = new JTextField();
        nameTextField.setText("");
        AddLogs.add(nameTextField, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        gachaTypeBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("常驻池");
        defaultComboBoxModel1.addElement("角色池");
        defaultComboBoxModel1.addElement("武器池");
        gachaTypeBox.setModel(defaultComboBoxModel1);
        AddLogs.add(gachaTypeBox, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        rankeBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("金色");
        defaultComboBoxModel2.addElement("紫色");
        rankeBox.setModel(defaultComboBoxModel2);
        AddLogs.add(rankeBox, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        CountTextField = new JTextField();
        CountTextField.setText("");
        AddLogs.add(CountTextField, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        final Spacer spacer3 = new Spacer();
        AddLogs.add(spacer3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        SubmitButton = new JButton();
        Font SubmitButtonFont = this.$$$getFont$$$(null, -1, 16, SubmitButton.getFont());
        if (SubmitButtonFont != null) SubmitButton.setFont(SubmitButtonFont);
        SubmitButton.setText("提交");
        AddLogs.add(SubmitButton, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 30), null, 0, false));
        final JLabel label9 = new JLabel();
        Font label9Font = this.$$$getFont$$$(null, -1, 16, label9.getFont());
        if (label9Font != null) label9.setFont(label9Font);
        label9.setText("物品名：");
        AddLogs.add(label9, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        uidlabel = new JLabel();
        Font uidlabelFont = this.$$$getFont$$$(null, -1, 16, uidlabel.getFont());
        if (uidlabelFont != null) uidlabel.setFont(uidlabelFont);
        uidlabel.setHorizontalTextPosition(11);
        uidlabel.setText("u  i  d：");
        AddLogs.add(uidlabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        Font label10Font = this.$$$getFont$$$(null, -1, 16, label10.getFont());
        if (label10Font != null) label10.setFont(label10Font);
        label10.setText("卡池：");
        AddLogs.add(label10, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        Font label11Font = this.$$$getFont$$$(null, -1, 16, label11.getFont());
        if (label11Font != null) label11.setFont(label11Font);
        label11.setText("稀有度：");
        AddLogs.add(label11, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        Font label12Font = this.$$$getFont$$$(null, -1, 16, label12.getFont());
        if (label12Font != null) label12.setFont(label12Font);
        label12.setText("抽卡数：");
        AddLogs.add(label12, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label13 = new JLabel();
        label13.setText("物品类别：");
        AddLogs.add(label13, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ItemType = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("角色");
        defaultComboBoxModel3.addElement("武器");
        ItemType.setModel(defaultComboBoxModel3);
        AddLogs.add(ItemType, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return root;
    }

}
