import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class DatabaseSelection extends JPanel
{

    public DatabaseSelection()
    {
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        databaseList = new DefaultListModel<String>();
        list = new JList<String>(databaseList);
        list.setSelectionMode(0);
        list.setVisibleRowCount(-1);
        GridBagConstraints listConstraints = new GridBagConstraints();
        listConstraints.anchor = 10;
        listConstraints.fill = 1;
        listConstraints.gridx = 1;
        listConstraints.gridy = 1;
        listConstraints.gridwidth = 2;
        listConstraints.insets.set(0, 0, 5, 0);
        add(list, listConstraints);
        next = new JButton("Next");
        GridBagConstraints nextButtonConstraints = new GridBagConstraints();
        nextButtonConstraints.anchor = 10;
        nextButtonConstraints.fill = 1;
        nextButtonConstraints.gridx = 2;
        nextButtonConstraints.gridy = 2;
        add(next, nextButtonConstraints);
        cancel = new JButton("Cancel");
        GridBagConstraints cancelButtonConstraints = new GridBagConstraints();
        cancelButtonConstraints.anchor = 10;
        cancelButtonConstraints.fill = 1;
        cancelButtonConstraints.insets.set(0, 0, 0, 5);
        cancelButtonConstraints.gridx = 1;
        cancelButtonConstraints.gridy = 2;
        add(cancel, cancelButtonConstraints);
    }

    public void setDatabaseList(ArrayList<String> databaseList)
    {
        databaseList.trimToSize();
        for(int i = 0; i < databaseList.size(); i++)
            this.databaseList.addElement((String)databaseList.get(i));

    }

    public String getSelection()
    {
        return (String)list.getSelectedValue();
    }

    public void nextListener(ActionListener listenForNext)
    {
        next.addActionListener(listenForNext);
    }

    public void cancelListener(ActionListener listenForCancel)
    {
        cancel.addActionListener(listenForCancel);
    }

    public void displayError(String message)
    {
        JOptionPane.showMessageDialog(this, message);
    }

    private static final long serialVersionUID = 1L;
    private JList<String> list;
    private DefaultListModel<String> databaseList;
    private JButton next;
    private JButton cancel;
}