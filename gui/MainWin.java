package gui;

import emporium.Emporium;

import product.IceCreamFlavor;
import product.MixInAmount;
import product.MixInFlavor;
import product.MixIn;
import product.Scoop;
import product.Container;
import product.Serving;
import product.Order;

import person.Customer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JDialog;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.Box;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;


public class MainWin extends JFrame {
    public MainWin(String title) {
        super(title);
        this.emporium = new Emporium();
        createGui();
    }

    private void createGui() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);

        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));

        // Center window
        setLocationRelativeTo(null);

        createMenubar();
        createToolbar();

        this.display = new JLabel();
        display.setLayout(new BoxLayout(display, BoxLayout.Y_AXIS));
        this.add(display, BorderLayout.CENTER);

        setOrderAvailability();
        setDirty(false);

        setVisible(true);
    }

    private void createMenubar() {
        // Create and add menubar to menu
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);

        // "File" menu
        JMenu file = new JMenu("File");
        menubar.add(file);

        JMenuItem openMI = new JMenuItem("Open");
        openMI.addActionListener(event -> onOpenClick());

        saveMI = new JMenuItem("Save");
        saveMI.addActionListener(event -> onSaveClick());
        saveMI.setEnabled(false);

        saveAsMI = new JMenuItem("Save As");
        saveAsMI.addActionListener(event -> onSaveAsClick());
        saveAsMI.setEnabled(false);

        JMenuItem quitMI = new JMenuItem("Quit");
        quitMI.addActionListener(event -> onQuitClick());

        file.add(openMI);
        file.add(saveMI);
        file.add(saveAsMI);
        file.add(quitMI);

        // "View" menu
        JMenu view = new JMenu("View");
        menubar.add(view);

        JMenuItem viewCusMI = new JMenuItem("Customer");
        viewCusMI.addActionListener(event -> view(Screen.CUSTOMERS));

        JMenuItem viewConMI = new JMenuItem("Container");
        viewConMI.addActionListener(event -> view(Screen.CONTAINERS));

        JMenuItem viewIceMI = new JMenuItem("Ice Cream Flavor");
        viewIceMI.addActionListener(event -> view(Screen.ICE_CREAM_FLAVORS));

        JMenuItem viewMixMI = new JMenuItem("Mix In Flavor");
        viewMixMI.addActionListener(event -> view(Screen.MIX_IN_FLAVORS));

        JMenuItem viewOrdMI = new JMenuItem("Order");
        viewOrdMI.addActionListener(event -> view(Screen.ORDERS));

        view.add(viewCusMI);
        view.add(viewConMI);
        view.add(viewIceMI);
        view.add(viewMixMI);
        view.add(viewOrdMI);

        // "Create" menu
        JMenu create = new JMenu("Create");
        menubar.add(create);

        JMenuItem createCusMI = new JMenuItem("Customer");
        createCusMI.addActionListener(event -> onCreateCustomerClick());

        JMenuItem createConMI = new JMenuItem("Container");
        createConMI.addActionListener(event -> onCreateContainerClick());

        JMenuItem createIceMI = new JMenuItem("Ice Cream Flavor");
        createIceMI.addActionListener(event -> onCreateIceCreamFlavorClick());

        JMenuItem createMixMI = new JMenuItem("Mix In Flavor");
        createMixMI.addActionListener(event -> onCreateMixInFlavorClick());

        createOrdMI = new JMenuItem("Order");
        createOrdMI.addActionListener(event -> onCreateOrderClick());
        createOrdMI.setEnabled(false);

        create.add(createCusMI);
        create.add(createConMI);
        create.add(createIceMI);
        create.add(createMixMI);
        create.add(createOrdMI);

        // Create and add help menu and its members to menubar
        JMenu help = new JMenu("Help");
        JMenuItem aboutMI = new JMenuItem("About");
        aboutMI.addActionListener(event -> onAboutClick());
        help.add(aboutMI);
        menubar.add(help);
    }

    private void createToolbar() {
        // Create toolbar
        JToolBar toolbar = new JToolBar("Actions", SwingConstants.HORIZONTAL);
        toolbar.setLayout(new FlowLayout(FlowLayout.CENTER, 4, 4));

        // Icons
        ImageIcon saveIcon = new ImageIcon("./gui/icons/save.png");
        ImageIcon saveAsIcon = new ImageIcon("./gui/icons/saveas.png");
        ImageIcon openIcon = new ImageIcon("./gui/icons/open.png");
        ImageIcon createCustomerIcon = new ImageIcon("./gui/icons/create-customer.png");
        ImageIcon createContainerIcon = new ImageIcon("./gui/icons/create-container.png");
        ImageIcon createIceCreamIcon = new ImageIcon("./gui/icons/create-ice-cream.png");
        ImageIcon createMixinIcon = new ImageIcon("./gui/icons/create-mixin.png");
        ImageIcon createScoopIcon = new ImageIcon("./gui/icons/create-scoop.png");
        ImageIcon viewCustomerIcon = new ImageIcon("./gui/icons/view-customer.png");
        ImageIcon viewContainerIcon = new ImageIcon("./gui/icons/view-container.png");
        ImageIcon viewIceCreamIcon = new ImageIcon("./gui/icons/view-ice-cream.png");
        ImageIcon viewMixinIcon = new ImageIcon("./gui/icons/view-mixin.png");
        ImageIcon viewScoopIcon = new ImageIcon("./gui/icons/view-scoop.png");

        // Buttons
        // File buttons
        saveB = new JButton(saveIcon);
        saveB.setToolTipText("Save your session to default file");
        saveB.addActionListener(event -> onSaveClick());

        saveAsB = new JButton(saveAsIcon);
        saveAsB.setToolTipText("Save your session to a file");
        saveAsB.addActionListener(event -> onSaveAsClick());

        JButton openB = new JButton(openIcon);
        openB.setToolTipText("Load your session from file");
        openB.addActionListener(event -> onOpenClick());

        // Create buttons
        JButton createCusB = new JButton(createCustomerIcon);
        createCusB.setToolTipText("Create a customer");
        createCusB.addActionListener(event -> onCreateCustomerClick());

        JButton createConB = new JButton(createContainerIcon);
        createConB.setToolTipText("Create a container");
        createConB.addActionListener(event -> onCreateContainerClick());

        JButton createIceB = new JButton(createIceCreamIcon);
        createIceB.setToolTipText("Create an ice cream flavor");
        createIceB.addActionListener(event -> onCreateIceCreamFlavorClick());

        JButton createMixB = new JButton(createMixinIcon);
        createMixB.setToolTipText("Create a mix in flavor");
        createMixB.addActionListener(event -> onCreateMixInFlavorClick());

        createOrdB = new JButton(createScoopIcon);
        createOrdB.setToolTipText("Create an order");
        createOrdB.addActionListener(event -> onCreateOrderClick());
        createOrdB.setEnabled(false);

        // View buttons
        JButton viewCusB = new JButton(viewCustomerIcon);
        viewCusB.setToolTipText("View all customers");
        viewCusB.addActionListener(event -> view(Screen.CUSTOMERS));

        JButton viewConB = new JButton(viewContainerIcon);
        viewConB.setToolTipText("View all containers");
        viewConB.addActionListener(event -> view(Screen.CONTAINERS));

        JButton viewIceB = new JButton(viewIceCreamIcon);
        viewIceB.setToolTipText("View all ice cream flavors");
        viewIceB.addActionListener(event -> view(Screen.ICE_CREAM_FLAVORS));

        JButton viewMixB = new JButton(viewMixinIcon);
        viewMixB.setToolTipText("View all mix in flavors");
        viewMixB.addActionListener(event -> view(Screen.MIX_IN_FLAVORS));

        JButton viewOrdB = new JButton(viewScoopIcon);
        viewOrdB.setToolTipText("View all orders");
        viewOrdB.addActionListener(event -> view(Screen.ORDERS));

        toolbar.add(saveB);
        toolbar.add(saveAsB);
        toolbar.add(openB);
        toolbar.addSeparator(new Dimension(5, 100));
        toolbar.add(createCusB);
        toolbar.add(createConB);
        toolbar.add(createIceB);
        toolbar.add(createMixB);
        toolbar.add(createOrdB);
        toolbar.addSeparator(new Dimension(5, 100));
        toolbar.add(viewCusB);
        toolbar.add(viewConB);
        toolbar.add(viewIceB);
        toolbar.add(viewMixB);
        toolbar.add(viewOrdB);
        toolbar.addSeparator(new Dimension(5, 100));

        toolbar.setFloatable(false);

        // Create scroll pane for toolbar
        JScrollPane scrollPane = new JScrollPane(toolbar, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane, BorderLayout.PAGE_START);
    }

    public void onOpenClick() {
        final JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter miceFiles = new FileNameExtensionFilter("MICE files", "mice");
        chooser.addChoosableFileFilter(miceFiles);
        chooser.setFileFilter(miceFiles);

        int returnValue = chooser.showOpenDialog(this);
        if(returnValue == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();

            String filename = file.getName();

            try(BufferedReader rw = new BufferedReader(new FileReader(file))) {
                String line = rw.readLine();
                if(!line.equals(MAGIC_COOKIE)) {
                    JOptionPane.showMessageDialog(
                            this,
                            String.format("\"%s\" is not a MICE file.", filename),
                            "Open Error",
                            JOptionPane.ERROR_MESSAGE);

                    return;
                }

                line = rw.readLine();
                if(!(line.equals(FILE_VERSION))) {
                    JOptionPane.showMessageDialog(
                            this,
                            String.format("\"%s\" has the wrong MICE file version.", filename),
                            "Open Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                rw.readLine();
                emporium = new Emporium(rw);
                setDirty(true);
            }
            catch(IOException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Unable to open" + file + "\n" + e,
                        "Open Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else if(returnValue == JFileChooser.ERROR_OPTION) {
            JOptionPane.showMessageDialog(this, "An error has occurred.", "Open Error", JOptionPane.ERROR_MESSAGE);
        }

        return;
    }

    public void onSaveClick() {
        if(file == null) {
            if(onSaveAsClick() != 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "You must create a new file to save session.",
                        "Save Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(MAGIC_COOKIE);
            bw.newLine();
            bw.write(FILE_VERSION);
            bw.newLine();
            emporium.save(bw);
        }
        catch(IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to save " + file + "\n" + e,
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return;
    }

    public int onSaveAsClick() {
        final JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter miceFiles = new FileNameExtensionFilter("MICE files", "mice");
        chooser.addChoosableFileFilter(miceFiles);
        chooser.setFileFilter(miceFiles);

        int returnVal = chooser.showSaveDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();

            if(!miceFiles.accept(file)) {
                file = new File(file.getAbsolutePath() + ".mice");
            }

            onSaveClick();
        }
        else {
            return -1;
        }

        return 0;
    }

    public void onQuitClick() {
        System.exit(0);
    }

    public void onCreateCustomerClick() {
        JTextField name = new JTextField();
        JTextField phone = new JTextField();

        Object[] fields = { "Name:", name, "Phone:", phone, };

        int choice = JOptionPane.showConfirmDialog(
                this,
                fields,
                "Create new Customer",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                new ImageIcon("./gui/icons/unedited/customer.png"));

        if(choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.CLOSED_OPTION) {
            return;
        }

        String strName = name.getText();
        String strPhone = phone.getText();

        Customer newCustomer = new Customer(strName, strPhone);
        emporium.addCustomer(newCustomer);

        view(Screen.CUSTOMERS);
        setDirty(true);
        setOrderAvailability();
    }

    public void onCreateContainerClick() {
        JTextField name = new JTextField();
        JTextField desc = new JTextField();
        SpinnerNumberModel numModel = new SpinnerNumberModel(1, 1, 100, 1);
        JSpinner maxScoops = new JSpinner(numModel);

        Object[] fields = { "Name:", name, "Description:", desc, "Max Scoops:", maxScoops };

        int choice = JOptionPane.showConfirmDialog(
                this,
                fields,
                "Create new Container",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                new ImageIcon("./gui/icons/unedited/container.png"));

        if(choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.CLOSED_OPTION) {
            return;
        }

        String strName = name.getText();
        String strDesc = desc.getText();
        int intMaxScoops = (int)maxScoops.getValue();

        Container newContainer = new Container(strName, strDesc, intMaxScoops);
        emporium.addContainer(newContainer);

        view(Screen.CONTAINERS);
        setDirty(true);
        setOrderAvailability();
    }

    public void onCreateIceCreamFlavorClick() {
        JTextField name = new JTextField();
        JTextField desc = new JTextField();
        SpinnerNumberModel priceModel = new SpinnerNumberModel(1, 1, 100, 1);
        SpinnerNumberModel costModel = new SpinnerNumberModel(1, 1, 100, 1);
        JSpinner price = new JSpinner(priceModel);
        JSpinner cost = new JSpinner(costModel);

        Object[] fields = { "Name:", name, "Description:", desc, "Price:", price, "Cost:", cost };

        int choice = JOptionPane.showConfirmDialog(
                this,
                fields,
                "Create New Ice Cream Flavor",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                new ImageIcon("./gui/icons/unedited/ice-cream.png"));

        if(choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.CLOSED_OPTION) {
            return;
        }

        String strName = name.getText();
        String strDesc = desc.getText();
        int priceInt = (int)price.getValue();
        int costInt = (int)cost.getValue();

        IceCreamFlavor newFlavor = new IceCreamFlavor(strName, strDesc, priceInt, costInt);
        emporium.addIceCreamFlavor(newFlavor);

        view(Screen.ICE_CREAM_FLAVORS);
        setDirty(true);
        setOrderAvailability();
    }

    public void onCreateMixInFlavorClick() {
        JTextField name = new JTextField();
        JTextField desc = new JTextField();
        SpinnerNumberModel priceModel = new SpinnerNumberModel(1, 1, 100, 1);
        SpinnerNumberModel costModel = new SpinnerNumberModel(1, 1, 100, 1);
        JSpinner price = new JSpinner(priceModel);
        JSpinner cost = new JSpinner(costModel);

        Object[] fields = { "Name:", name, "Description:", desc, "Price:", price, "Cost:", cost };

        int choice = JOptionPane.showConfirmDialog(
                this,
                fields,
                "Create New MixIn Flavor",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                new ImageIcon("./gui/icons/unedited/mixin.png"));

        if(choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.CLOSED_OPTION) {
            return;
        }

        String strName = name.getText();
        String strDesc = desc.getText();
        int priceInt = (int)price.getValue();
        int costInt = (int)cost.getValue();

        MixInFlavor newFlavor = new MixInFlavor(strName, strDesc, priceInt, costInt);
        emporium.addMixInFlavor(newFlavor);

        view(Screen.MIX_IN_FLAVORS);
        setDirty(true);
    }

    public MixIn onCreateMixIn(String object) {
        Object[] mixInFlavors = emporium.mixInFlavors();
        Object[] mixInAmounts = MixInAmount.values();

        JLabel mixInFlavorLabel = new JLabel("Mix In Flavor:");
        JLabel mixInAmountLabel = new JLabel("Mix In Amount:");
        JComboBox<Object> mixInFlavorsBox = new JComboBox<>(mixInFlavors);
        JComboBox<Object> mixInAmountsBox = new JComboBox<>(mixInAmounts);

        Object[] fields = { object, mixInFlavorLabel, mixInFlavorsBox, mixInAmountLabel, mixInAmountsBox };

        int choice = JOptionPane.showConfirmDialog(
                this,
                fields,
                "Create New Mix In",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                new ImageIcon("./gui/icons/unedited/mixin.png"));

        if(choice == JOptionPane.NO_OPTION || choice == JOptionPane.CLOSED_OPTION) {
            return null;
        }

        MixInFlavor userMixInFlavor = (MixInFlavor)mixInFlavorsBox.getSelectedItem();
        MixInAmount userMixInAmount = (MixInAmount)mixInAmountsBox.getSelectedItem();

        MixIn userMixIn = new MixIn(userMixInFlavor, userMixInAmount);
        return userMixIn;
    }

    public Scoop onCreateScoop() {
        Object[] iceFlavors = emporium.iceCreamFlavors();
        JLabel iceCreamFlavor = new JLabel("New Scoop?");

        Object choice = JOptionPane.showInputDialog(
                this,
                iceCreamFlavor,
                "Create new Scoop",
                JOptionPane.QUESTION_MESSAGE,
                new ImageIcon("./gui/icons/unedited/scoop.png"),
                iceFlavors,
                null);

        if(choice == null) {
            return null;
        }

        IceCreamFlavor userIceFlavor = (IceCreamFlavor)choice;

        Scoop userScoop = new Scoop(userIceFlavor);
        MixIn userMixIn = null;

        while((emporium.mixInFlavors()).length > 0) {
            userMixIn = onCreateMixIn("Add MixIn for: " + "\"" + userScoop.toString() + "\"\n\n");

            if(userMixIn == null) {
                break;
            }
            userScoop.addMixIn(userMixIn);

            int another = JOptionPane.showConfirmDialog(
                    this,
                    "Another Mix In for " + "\"" + userScoop + "\"",
                    "Another Mix In",
                    JOptionPane.YES_NO_OPTION);

            if(another == JOptionPane.NO_OPTION) {
                break;
            }
        }

        return userScoop;
    }

    public Serving onCreateServing() {
        Object choice = new Object();

        Object[] containers = emporium.containers();
        JLabel containerLabel = new JLabel("Add Container?");
        choice = JOptionPane.showInputDialog(
                this,
                containerLabel,
                "Add new Serving",
                JOptionPane.QUESTION_MESSAGE,
                new ImageIcon("./gui/icons/unedited/scoop.png"),
                containers,
                null);

        if(choice == null) {
            return null;
        }
        Container userContainer = (Container)choice;

        Serving userServing = new Serving(userContainer);
        Scoop userScoop = null;
        int numScoops = 0;
        int maxScoops = userContainer.maxScoops();

        while(numScoops < maxScoops) {
            userScoop = onCreateScoop();

            if(userScoop == null && numScoops == 0) {
                return null;
            }

            if(userScoop == null) {
                break;
            }

            userServing.addScoop(userScoop);
            ++numScoops;

            int another = JOptionPane.showConfirmDialog(
                    this,
                    "Another scoop for: " + "\"" + userServing.toString() + "\"",
                    "Another scoop for serving?",
                    JOptionPane.YES_NO_OPTION);

            if(another == JOptionPane.NO_OPTION || another == JOptionPane.CLOSED_OPTION) {
                break;
            }
        }

        // Toppings
        Object[] mixInFlavors = emporium.mixInFlavors();
        MixIn userMixIn = null;

        while(mixInFlavors.length > 0) {
            userMixIn = onCreateMixIn("Add Topping for: " + "\"" + userServing.toString() + "\"\n\n");

            if(userMixIn == null) {
                break;
            }

            userServing.addTopping(userMixIn);
        }

        return userServing;
    }

    public void onCreateOrderClick() {
        JLabel orders = new JLabel();
        int howManyServe = 0;

        Object choice = JOptionPane.showInputDialog(
                this,
                new JLabel("Who is ordering?"),
                "Pick customer",
                JOptionPane.QUESTION_MESSAGE,
                new ImageIcon("./gui/icons/unedited/customer.png"),
                emporium.customers(),
                null);
        if(choice == null) {
            return;
        }
        Order userOrder = new Order(((Customer)choice));

        while(true) {
            Serving userServing = onCreateServing();
            if(howManyServe == 0 && userServing == null) {
                return;
            }

            if(userServing == null) {
                break;
            }

            userOrder.addServing(userServing);
            ++howManyServe;

            orders = new JLabel(userOrder.toString());
            int anotherOption = JOptionPane
                    .showConfirmDialog(this, orders, "Another Serving?", JOptionPane.YES_NO_OPTION);
            if(anotherOption == JOptionPane.NO_OPTION || anotherOption == JOptionPane.CLOSED_OPTION) {
                break;
            }
        }

        if(howManyServe > 0) {
            emporium.addOrder(userOrder);
            view(Screen.ORDERS);
            setDirty(true);
        }
    }

    public void onAboutClick() {
        JDialog about = new JDialog(this, "About");
        about.setLayout(new BoxLayout(about.getContentPane(), BoxLayout.X_AXIS));

        Canvas canvas = new Canvas();
        canvas.setAlignmentX(Canvas.LEFT_ALIGNMENT);
        canvas.setBorder(new EmptyBorder(25, 25, 25, 25));
        // canvas.setLayout(new BorderLayout());
        about.add(canvas);

        about.setSize(200, 200);
        about.setResizable(false);
        about.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // canvas.setLayout(new BoxLayout(canvas,BoxLayout.Y_AXIS));
        about.pack();

        String aboutInfo = "<html>" + "<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><hr>"
                + "<head><title><font size =+5><center>MICE</center></font></title></head>" + "<body>"
                + "<div style=\"text-align: center;\">" + "<font size =+1>Mavs Ice Cream Emporium</font>" + "</div><br>"
                + "Version 0.2" + "<br>" + "Copyright 2022 by Emilio Quinones" + "<br>" + "Licensed under GNU GPL 3.0"
                + "<br><hr>"

                + "Pictures used in code"
                + "<br><font size =-1> - Floppy Disk Icon by Those Icons, per the FlatIcon License:</font>"
                + "<br><font size =-3>https://www.flaticon.com/free-icons/save</font>"

                + "<br><font size =-1> - File Open Icon by OpenClipart, per the Public Domain License:</font>"
                + "<br><font size =-3><https://freesvg.org/file-open-icon/font>"

                + "<br><font size =-1> - Single Ice Cream Cone Icon by Good Ware, per the FlatIcon License:</font>"
                + "<br><font size =-3>https://www.flaticon.com/free-icons/ice-cream</font>"

                + "<br><font size =-1> - Chocolate Bar Icon by dDara, per the FlatIcon License:</font>"
                + "<br><font size =-3>https://www.flaticon.com/free-icons/chocolate</font>"

                + "<br><font size =-1> - Ice Cream Cup Icon by monkik, per the FlatIcon License:</font>"
                + "<br><font size =-3>https://www.flaticon.com/free-icons/ice-cream</font>"

                + "<br><font size =-1> - Container Glass Beaker Jar by OpenClipart-Vectors per Pixabay License:</font>"
                + "<br><font size =-3>https://pixabay.com/vectors/jar-glass-beaker-container-151611/</font>"

                + "<br><font size =-1> - User icon silhouette by OpenClipart per Public Domain:</font>"
                + "<br><font size =-3>https://freesvg.org/user-icon-silhouette"

                + "<br><font size =-1> - Logo Icon by OpenClipart, per the Public Domain License:</font>"
                + "<br><font size =-3>https://freesvg.org/gerald-g-soft-ice-cream-cones-ff-menu</font>"

                + "<br><font size =-1> - Eye Icon by OpenClipart, per the FlatIcon License:</font>"
                + "<br><font size =-3>https://www.flaticon.com/free-icons/eye</font>"

                + "<br><font size =-1> - Open icon by IconKanan, per Public Domain License:</font>"
                + "<br><font size =-3>https://www.flaticon.com/free-icons/share</font>"

                + "<br><font size =-1> - Create icon by Ilham Fitrotul Hayat, per the FlatIcon License</font>"
                + "<br><font size =-3>https://www.flaticon.com/free-icons/create</font>"

                // + "<br><font size =-1> - Single Ice Cream Cone with Create Icon by Emilio
                // Quinones:</font>"
                // + "<br><font size =-3>https://www.flaticon.com/free-icons/ice-cream</font>"
                // + "<br><font size =-3>https://www.flaticon.com/free-icons/create</font>"

                // + "<br><font size =-1> - Chocolate Bar with Create Icon by Emilio
                // Quinones:</font>"
                // + "<br><font size =-3>https://www.flaticon.com/free-icons/chocolate</font>"
                // + "<br><font size =-3>https://www.flaticon.com/free-icons/create</font>"

                // + "<br><font size =-1> - Ice Cream Cup with Create Icon by Emilio
                // Quinones:</font>"
                // + "<br><font size =-3>https://www.flaticon.com/free-icons/ice-cream</font>"
                // + "<br><font size =-3>https://www.flaticon.com/free-icons/create</font>"

                // + "<br><font size =-1> - Container Glass Beaker Jar with Create Icon by
                // Emilio Quinones:</font>"
                // + "<br><font size
                // =-3>https://pixabay.com/vectors/jar-glass-beaker-container-151611/</font>"
                // + "<br><font size =-3>https://www.flaticon.com/free-icons/create</font>"

                // + "<br><font size =-1> - User icon silhouette with Create Icon by Emilio
                // Quinones:</font>"
                // + "<br><font size =-3>https://freesvg.org/user-icon-silhouette"
                // + "<br><font size =-3>https://www.flaticon.com/free-icons/eye</font>"

                // + "<br><font size =-1> - Single Ice Cream Cone with Eye Icon by Emilio
                // Quinones:</font>"
                // + "<br><font size =-3>https://www.flaticon.com/free-icons/ice-cream</font>"
                // + "<br><font size =-3>https://www.flaticon.com/free-icons/eye</font>"

                // + "<br><font size =-1> - Chocolate Bar with Eye Icon by Emilio
                // Quinones:</font>"
                // + "<br><font size =-3>https://www.flaticon.com/free-icons/chocolate</font>"
                // + "<br><font size =-3>https://www.flaticon.com/free-icons/eye</font>"

                // + "<br><font size =-1> - Ice Cream Cup with Eye Icon by Emilio
                // Quinones:</font>"
                // + "<br><font size =-3>https://www.flaticon.com/free-icons/ice-cream</font>"
                // + "<br><font size =-3>https://www.flaticon.com/free-icons/eye</font>"

                // + "<br><font size =-1> - Container Glass Beaker Jar with Eye Icon by Emilio
                // Quinones:</font>"
                // + "<br><font size
                // =-3>https://pixabay.com/vectors/jar-glass-beaker-container-151611/</font>"
                // + "<br><font size =-3>https://www.flaticon.com/free-icons/eye</font>"

                // + "<br><font size =-1> - User icon silhouette with Eye Icon by Emilio
                // Quinones:</font>"
                // + "<br><font size =-3>https://freesvg.org/user-icon-silhouette"
                // + "<br><font size =-3>https://www.flaticon.com/free-icons/eye</font>"

                // +https://svgsilh.com/image/151611.html
                + "</br></body></html>";

        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.X_AXIS));
        JLabel info = new JLabel(aboutInfo, JLabel.CENTER);
        canvas.add(info);
        about.add(canvas);
        about.add(Box.createVerticalStrut(100));
        about.pack();
        about.setVisible(true);
    }

    private void view(Screen screen) {
        StringBuilder finalString = new StringBuilder();
        String title = new String();
        String newLine = "<br/>";
        int i = 0;

        if(screen == Screen.CONTAINERS) {
            title = "Containers" + newLine;
            for(Object s : emporium.containers()) {
                finalString.append(((Container)s).toString() + newLine);
            }
        }

        if(screen == Screen.ICE_CREAM_FLAVORS) {
            title = "Ice Cream Flavors" + newLine;
            for(Object s : emporium.iceCreamFlavors()) {
                finalString.append(((IceCreamFlavor)s).toString() + newLine);
            }
        }

        if(screen == Screen.MIX_IN_FLAVORS) {
            title = "Mix In Flavors" + newLine;
            for(Object s : emporium.mixInFlavors()) {
                finalString.append(((MixInFlavor)s).toString() + newLine);
            }
        }

        if(screen == Screen.ORDERS) {
            title = "Orders" + newLine;
            for(Object s : emporium.orders()) {
                finalString.append(
                        "Order " + ++i + " $" + ((Order)s).price() + "for " + ((Order)s).getCustomer() + newLine);
                finalString.append(((Order)s).toString() + newLine);
            }
            i = 0;
        }

        display.setText(
                "<html><font size =+5><b><u>" + title + "</u></b></font>" + "<font size=+2>" + finalString.toString()
                        + "</font></html>");
    }

    private void setDirty(boolean isDirty) {
        saveMI.setEnabled(isDirty);
        saveAsMI.setEnabled(isDirty);
        saveB.setEnabled(isDirty);
        saveAsB.setEnabled(isDirty);
    }

    private void setOrderAvailability() {
        int numCon = emporium.containers().length;
        int numIce = emporium.iceCreamFlavors().length;
        int numCus = emporium.customers().length;

        if(numCon > 0 && numIce > 0 && numCus > 0) {
            createOrdMI.setEnabled(true);
            createOrdB.setEnabled(true);
        }
        else {
            createOrdMI.setEnabled(false);
            createOrdB.setEnabled(false);
        }
    }

    private JMenuItem saveMI;
    private JMenuItem saveAsMI;
    private JMenuItem createOrdMI;

    private JButton saveB;
    private JButton saveAsB;
    private JButton createOrdB;

    final static String MAGIC_COOKIE = "ꬺICƐ🧊🍨";
    final static String FILE_VERSION = "1.1";

    private Emporium emporium;
    private JLabel display;
    private File file;

    private enum Screen {
        CUSTOMERS, CONTAINERS, ICE_CREAM_FLAVORS, MIX_IN_FLAVORS, ORDERS
    }
}
