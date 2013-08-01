/***********************************************************************
 * 
 *     Copyright: 2011, BAINA Technologies Co. Ltd.
 *     Classname: ButtonDesignPanel.java
 *     Author:    yuewang
 *     Description:    TODO
 *     History:
 *         1.  Date:   下午10:37:02
 *             Author:    yuewang
 *             Modifycation:    create the class.       
 *
 ***********************************************************************/

package org.lichsword.android.ui.designPanels;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.lichsword.android.manager.BuildProcessListener;
import org.lichsword.android.manager.IconCachedManager;
import org.lichsword.android.manager.RefactorFileManager;
import org.lichsword.android.model.activity.ButtonInfo;
import org.lichsword.android.ui.view.advance.DragFileHandler;
import org.lichsword.util.FileUtil;
import org.lichsword.util.TipUtil;

/**
 * @author yuewang
 * 
 */
public class ButtonDesignPanel extends JPanel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -7426500393851049599L;

    /**
	 * 
	 */
    public ButtonDesignPanel() {
        super();
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);
        initContentView();
    }

    private JTextField mKeywordEditText;
    private JLabel mDefaultPiclabel;
    private JLabel mPressedPiclabel;
    private JLabel mDisabledPiclabel;
    private JButton mConfirmButton;
    private JTextArea mProcessTextArea;
    private JLabel mDefaultPicDescLabel;
    private JLabel mPressedPicDescLabel;
    private JLabel mDisabledPicDescLabel;
    private JCheckBox mAutoOpenDirCheckBox;
    private JLabel mTipLabel;
    private JCheckBox mAutoResetChcekBox;
    private JCheckBox mDefaultPictureCheckBox;
    private JCheckBox mPressedPictureCheckBox;
    private JCheckBox mDisabledPictureCheckBox;

    private final DragFileHandler mDefaultPicDragFileHandler = new DragFileHandler() {

        /**
		 * 
		 */
        private static final long serialVersionUID = 1L;

        @Override
        public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
            return super.canImport(comp, transferFlavors);
        }

        @Override
        public boolean importData(JComponent comp, Transferable t) {
            try {

                List dragFileList = (List) t
                        .getTransferData(DataFlavor.javaFileListFlavor);
                String filePath = loadDragFiles(dragFileList);
                dragFileEventHappened(filePath, ButtonInfo.PIC_DEFAULT);
            } catch (UnsupportedFlavorException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return super.importData(comp, t);
        }

    };

    private final DragFileHandler mPressedPicDragFileHandler = new DragFileHandler() {

        /**
		 * 
		 */
        private static final long serialVersionUID = 2L;

        @Override
        public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
            return super.canImport(comp, transferFlavors);
        }

        @Override
        public boolean importData(JComponent comp, Transferable t) {
            try {

                List dragFileList = (List) t
                        .getTransferData(DataFlavor.javaFileListFlavor);
                String filePath = loadDragFiles(dragFileList);
                dragFileEventHappened(filePath, ButtonInfo.PIC_PRESSED);
            } catch (UnsupportedFlavorException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return super.importData(comp, t);
        }

    };

    private final DragFileHandler mDisabledPicDragFileHandler = new DragFileHandler() {

        /**
		 * 
		 */
        private static final long serialVersionUID = 3L;

        @Override
        public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
            return super.canImport(comp, transferFlavors);
        }

        @Override
        public boolean importData(JComponent comp, Transferable t) {
            try {

                List dragFileList = (List) t
                        .getTransferData(DataFlavor.javaFileListFlavor);
                String filePath = loadDragFiles(dragFileList);
                dragFileEventHappened(filePath, ButtonInfo.PIC_DISABLED);
            } catch (UnsupportedFlavorException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return super.importData(comp, t);
        }

    };

    private String loadDragFiles(List dragFileList) {
        String filePath = null;
        for (Iterator iter = dragFileList.iterator(); iter.hasNext();) {
            File file = (File) iter.next();
            filePath = file.getPath();
            break;
        }
        return filePath;
    }

    private boolean dragFileEventHappened(String filePath, int whichPic) {
        if (!FileUtil.isSupportPicType(filePath)) {
            System.out
                    .println("Not belong supported picture type[9.png/png/jpg]...ERROR");
            return false;
        }// end if

        // cached icon
        IconCachedManager.getInstance()
                .DragFileChangeHappen(filePath, whichPic);
        // cached icon info
        switch (whichPic) {
        case ButtonInfo.PIC_DEFAULT:
            System.out.println("default pic : " + filePath);
            mDefaultPiclabel.setIcon(IconCachedManager.getInstance().getIcon(
                    filePath));
            break;
        case ButtonInfo.PIC_PRESSED:
            System.out.println("pressed pic : " + filePath);
            mPressedPiclabel.setIcon(IconCachedManager.getInstance().getIcon(
                    filePath));
            break;
        case ButtonInfo.PIC_DISABLED:
            System.out.println("disabled pic : " + filePath);
            mDisabledPiclabel.setIcon(IconCachedManager.getInstance().getIcon(
                    filePath));
            break;
        default:
            break;
        }
        return true;
    }

    private final ActionListener mConfirmActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("You click confirm button...OK");
            String keyword = mKeywordEditText.getText();
            if (FileUtil.isKeywordSafe(keyword)) {
                if (preCheckButtonInfo()) {
                    IconCachedManager.getInstance().confirmBuildButton(keyword);
                }// end if
            } else {
                System.out
                        .println("Keyword contain illegal char, only allow [0~9] OR [a~z] OR '_'");
                showTip(TipUtil.ERROR_CONTAIN_ILLEGAL_CHAR);
            }
        }

    };

    private boolean preCheckButtonInfo() {
        boolean canPassCheck = true;

        String tip = IconCachedManager.getInstance().preCheckButtonInfo(
                mDefaultPictureCheckBox.isSelected(),
                mPressedPictureCheckBox.isSelected(),
                mDisabledPictureCheckBox.isSelected());
        if (!tip.equals(TipUtil.INFO_CHECK_PICTIRE_PASSED)) {
            showTip(tip);
            canPassCheck = false;
        } else {
            canPassCheck = true;
        }
        return canPassCheck;
    }

    private final KeyListener mKeywordKeyListener = new KeyListener() {

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
            System.out.println("You typed " + e.getKeyChar());
            char ch = e.getKeyChar();
            if (FileUtil.isCharSafe(ch)) {
                hideTip();
                refreshPicDesc(mKeywordEditText.getText() + ch);
            } else {
                System.out
                        .println("Illegal character input: "
                                + ch
                                + "[Only support ：1.number(0~9) 2.low char(a~z) 3.underline(_)]");
                showTip(TipUtil.ERROR_CONTAIN_ILLEGAL_CHAR);
            }
        }

    };

    private void refreshPicDesc(String keyword) {
        //
        String pressedText = IconCachedManager.getInstance().getRefactName(
                keyword, ButtonInfo.PIC_PRESSED);
        mPressedPicDescLabel.setText(pressedText);
        //
        String disabledText = IconCachedManager.getInstance().getRefactName(
                keyword, ButtonInfo.PIC_DISABLED);
        mDisabledPicDescLabel.setText(disabledText);
        //
        String defauledText = IconCachedManager.getInstance().getRefactName(
                keyword, ButtonInfo.PIC_DEFAULT);
        mDefaultPicDescLabel.setText(defauledText);
    }

    @SuppressWarnings("unused")
    private boolean limitKeywordSafe(String src) {
        byte[] buffer = new byte[100];

        int index = 0;
        // limit to no space char
        byte[] bytes = src.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            byte temp = bytes[i];
            if (temp >= 'A' && temp <= 'Z') {
                // limit to lowcase
                temp += 32;
            } else if (temp >= '0' && temp <= '9') {
                // allow, do nothing
            } else if (temp == '_') {
                // allow, do nothing
            } else if (temp >= 'a' && temp <= 'z') {
                // allow, do nothing
            } else {
                continue;
            }
            buffer[index] = bytes[i];
            index++;
        }
        mKeywordEditText.setText(new String(buffer, 0, index));
        return true;
    }

    private final BuildProcessListener mBuildProcessListener = new BuildProcessListener() {

        @Override
        public void buildBegin(String msg) {
            enableAllCompoment(false);
            mProcessTextArea.setText("");
        }

        @Override
        public void buildEnd(String msg) {
            enableAllCompoment(true);
            if (mAutoOpenDirCheckBox.isSelected()) {
                FileUtil.autoOpenDir(RefactorFileManager.getInstance()
                        .getTargetDrawableDirPath());
                FileUtil.autoOpenDir(RefactorFileManager.getInstance()
                        .getTargetDrawableHdpiDirPath());
                System.out
                        .println("Auto open folder HAS select, auto open folder...OK");
            } else {
                System.out
                        .println("Auto open folder is NOT select, so do nothing...OK");
            }

            if (mAutoResetChcekBox.isEnabled()) {
                IconCachedManager.getInstance().clearAllInfo();
                resetAllCompoment();
            }// end if
        }

        @Override
        public void buildProcess(String msg, int color) {
            if (null != msg) {
                mProcessTextArea.append(msg);
                mProcessTextArea.append("\n");
            }// end if
        }

    };

    private void enableAllCompoment(boolean enable) {
        mConfirmButton.setEnabled(enable);
        mDefaultPiclabel.setEnabled(enable);
        mPressedPiclabel.setEnabled(enable);
        mDisabledPiclabel.setEnabled(enable);
    }

    private void resetAllCompoment() {
        mDefaultPicDescLabel.setText("");
        mPressedPicDescLabel.setText("");
        mDisabledPicDescLabel.setText("");
        mTipLabel.setText("");
        mDefaultPiclabel.setIcon(null);
        mPressedPiclabel.setIcon(null);
        mDisabledPiclabel.setIcon(null);
        mKeywordEditText.setText("");
    }

    private void showTip(String tipMsg) {
        mTipLabel.setText(tipMsg);
    }

    private void hideTip() {
        mTipLabel.setText("");
    }

    /**
     * Create the frame.
     */
    private void initContentView() {
        // ============================================================
        {
            JPanel jPanel1 = new JPanel();
            jPanel1.setBackground(SystemColor.info);
            jPanel1.setBounds(57, 39, 120, 120);
            add(jPanel1);
            jPanel1.setLayout(null);
            {
                mDefaultPiclabel = new JLabel("Default Picture");
                mDefaultPiclabel.setBounds(0, 0, 120, 120);
                jPanel1.add(mDefaultPiclabel);
                mDefaultPiclabel.setHorizontalAlignment(SwingConstants.CENTER);
                mDefaultPiclabel.setBackground(Color.WHITE);
                mDefaultPiclabel.setTransferHandler(mDefaultPicDragFileHandler);
            }
        }
        // ============================================================
        {
            JPanel jPanel2 = new JPanel();
            jPanel2.setBackground(SystemColor.info);
            jPanel2.setBounds(252, 39, 120, 120);
            add(jPanel2);
            jPanel2.setLayout(null);
            {
                mPressedPiclabel = new JLabel("Pressed Picture");
                mPressedPiclabel.setBounds(0, 0, 120, 120);
                jPanel2.add(mPressedPiclabel);
                mPressedPiclabel.setHorizontalAlignment(SwingConstants.CENTER);
                mPressedPiclabel.setBackground(Color.ORANGE);
                mPressedPiclabel.setTransferHandler(mPressedPicDragFileHandler);
            }
        }
        // ============================================================
        {
            JPanel jPanel3 = new JPanel();
            jPanel3.setBackground(SystemColor.info);
            jPanel3.setBounds(437, 39, 120, 120);
            add(jPanel3);
            jPanel3.setLayout(null);
            {
                mDisabledPiclabel = new JLabel("Disabled Picture");
                mDisabledPiclabel.setBounds(0, 0, 120, 120);
                jPanel3.add(mDisabledPiclabel);
                mDisabledPiclabel.setHorizontalAlignment(SwingConstants.CENTER);
                mDisabledPiclabel.setBackground(Color.DARK_GRAY);
                mDisabledPiclabel
                        .setTransferHandler(mDisabledPicDragFileHandler);
            }
        }

        mConfirmButton = new JButton("Confirm");
        mConfirmButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
        mConfirmButton.setBounds(387, 241, 160, 45);
        mConfirmButton.addActionListener(mConfirmActionListener);
        add(mConfirmButton);

        mProcessTextArea = new JTextArea();
        mProcessTextArea.setEditable(false);
        mProcessTextArea.setLineWrap(true);
        mProcessTextArea.setWrapStyleWord(true);
        mProcessTextArea.setBounds(52, 241, 300, 198);
        add(mProcessTextArea);

        mDefaultPicDescLabel = new JLabel();
        mDefaultPicDescLabel.setBounds(52, 158, 160, 24);
        add(mDefaultPicDescLabel);

        mPressedPicDescLabel = new JLabel();
        mPressedPicDescLabel.setBounds(241, 158, 160, 24);
        add(mPressedPicDescLabel);

        mDisabledPicDescLabel = new JLabel();
        mDisabledPicDescLabel.setBounds(424, 158, 160, 24);
        add(mDisabledPicDescLabel);

        mAutoOpenDirCheckBox = new JCheckBox("Auto open folder");
        mAutoOpenDirCheckBox.setSelected(true);
        mAutoOpenDirCheckBox.setBounds(387, 293, 163, 23);
        add(mAutoOpenDirCheckBox);

        JLabel label2 = new JLabel("Sort progress :");
        label2.setBounds(52, 214, 182, 26);
        add(label2);

        JLabel label1 = new JLabel("Input keyword:");
        label1.setBounds(387, 383, 139, 14);
        add(label1);

        mKeywordEditText = new JTextField();
        mKeywordEditText.setBounds(387, 408, 160, 31);
        mKeywordEditText.addKeyListener(mKeywordKeyListener);
        mKeywordEditText.setColumns(10);
        add(mKeywordEditText);

        mTipLabel = new JLabel();
        mTipLabel.setFont(new Font("Monospaced", Font.BOLD, 13));
        mTipLabel.setForeground(Color.RED);
        mTipLabel.setBounds(52, 450, 532, 24);
        add(mTipLabel);
        hideTip();

        mAutoResetChcekBox = new JCheckBox("Auto reset after finish");
        mAutoResetChcekBox.setBounds(387, 319, 163, 23);
        add(mAutoResetChcekBox);

        mDefaultPictureCheckBox = new JCheckBox("check defult picture");
        mDefaultPictureCheckBox.setBounds(49, 189, 163, 23);
        add(mDefaultPictureCheckBox);

        mPressedPictureCheckBox = new JCheckBox("check pressed picture");
        mPressedPictureCheckBox.setBounds(241, 189, 163, 23);
        add(mPressedPictureCheckBox);

        mDisabledPictureCheckBox = new JCheckBox("check disabled picture");
        mDisabledPictureCheckBox.setBounds(424, 189, 163, 23);
        add(mDisabledPictureCheckBox);

        // init set listener
        initListeners();
    }

    private void initListeners() {
        IconCachedManager.getInstance().setBuildProcessListener(
                mBuildProcessListener);
    }
}
