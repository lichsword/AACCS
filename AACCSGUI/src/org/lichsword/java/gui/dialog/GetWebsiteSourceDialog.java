/******************************************************************************
 *
 * Copyright 2012 Lichsword Studio, All right reserved.
 *
 * File name   : GetWebsiteSourceDialog.java
 * Create time : 2012-10-27
 * Author      : lichsword
 * Description : TODO
 *
 *****************************************************************************/
package org.lichsword.java.gui.dialog;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.lichsword.java.util.HttpUtil;
import org.lichsword.java.util.TextUtil;

public class GetWebsiteSourceDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = -4500707133247109310L;
	private JTextField textFieldURL;
	private JTextField textFieldSourceCode;
	private JCheckBox checkBoxAutoCopyToClipboard;
	private JButton buttonStart;
	private JComboBox<String> comboBoxEncoding;

	private final String DIALOG_TITLE = "抓取网页源代码";
	private final String DIALOG_LABEL_TEXT_WEBSITE = "网址:";
	private final String DIALOG_LABEL_TEXT_SOURCE_CODE = "网页源代码";
	private final String DIALOG_BUTTON_TEXT_START = "开始抓取:";
	private final String DIALOG_CHECKBOX_TEXT_AUTO_COPY_TO_CLIPBOARD = "自动复制到剪切板";

	public GetWebsiteSourceDialog(boolean modal) {
		setModal(modal);
		initContentView();
		fillContentView();
	}

	private void initContentView() {
		setBounds(new Rectangle(0, 0, 600, 320));
		setTitle(DIALOG_TITLE);

		JLabel label = new JLabel(DIALOG_LABEL_TEXT_WEBSITE);

		textFieldURL = new JTextField();
		textFieldURL.setColumns(10);

		textFieldSourceCode = new JTextField();
		textFieldSourceCode.setColumns(10);

		JLabel label_1 = new JLabel(DIALOG_LABEL_TEXT_SOURCE_CODE);
		buttonStart = new JButton(DIALOG_BUTTON_TEXT_START);
		buttonStart.setLocation(new Point(600, 400));
		buttonStart.setSize(new Dimension(600, 320));
		buttonStart.addActionListener(this);

		checkBoxAutoCopyToClipboard = new JCheckBox(
				DIALOG_CHECKBOX_TEXT_AUTO_COPY_TO_CLIPBOARD);
		checkBoxAutoCopyToClipboard.addActionListener(this);

		String array[] = { "utf-8", "gb2312" };
		comboBoxEncoding = new JComboBox<String>(array);
		comboBoxEncoding.addItemListener(new EncodingItemSelectListener());
		//
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																textFieldSourceCode,
																GroupLayout.PREFERRED_SIZE,
																517,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				label)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				textFieldURL,
																				GroupLayout.DEFAULT_SIZE,
																				524,
																				Short.MAX_VALUE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				label_1)
																		.addGap(
																				18)
																		.addComponent(
																				checkBoxAutoCopyToClipboard)
																		.addGap(
																				78)
																		.addComponent(
																				comboBoxEncoding,
																				GroupLayout.PREFERRED_SIZE,
																				138,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				92,
																				Short.MAX_VALUE)
																		.addComponent(
																				buttonStart)))
										.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addContainerGap().addGroup(
						groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(label).addComponent(textFieldURL,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)).addGap(18)
						.addGroup(
								groupLayout.createParallelGroup(
										Alignment.BASELINE).addComponent(
										label_1).addComponent(buttonStart)
										.addComponent(
												checkBoxAutoCopyToClipboard)
										.addComponent(comboBoxEncoding,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addGap(33).addComponent(textFieldSourceCode,
								GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
						.addContainerGap()));
		getContentPane().setLayout(groupLayout);
	}

	private void fillContentView() {
		encoding = comboBoxEncoding.getSelectedItem().toString();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = e.getActionCommand();
		Object object = e.getSource();
		if (object instanceof JButton) {
			OnButtonClickEvent(name);
		} else if (object instanceof JCheckBox) {
			OnCheckBoxClickEvent(name);
		}// end if
	}

	private String encoding;

	private class EncodingItemSelectListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			int state = e.getStateChange();
			if (state == ItemEvent.SELECTED) {
				String selectText = comboBoxEncoding.getSelectedItem()
						.toString();
				System.out.println("" + selectText);
				encoding = selectText;
			}
		}
	}

	private void OnCheckBoxClickEvent(String name) {
		if (name.equals(DIALOG_CHECKBOX_TEXT_AUTO_COPY_TO_CLIPBOARD)) {
			// TODO
		}
	}

	private void OnButtonClickEvent(String name) {
		if (name.equals(DIALOG_BUTTON_TEXT_START)) {
			final String url = textFieldURL.getText();
			if (!TextUtil.isEmpty(url)) {
				Thread thread = new Thread() {
					@Override
					public void run() {
						String sourceCode = HttpUtil.getSourceCode(url,
								encoding);
						textFieldSourceCode.setText(sourceCode);
						if (null != mListener) {
							mListener.finishGetWebsiteCode(!TextUtil
									.isEmpty(sourceCode), sourceCode);
						}// end if
						super.run();
					}
				};
				thread.start();
			} else {

			}
		}
	}

	private Listener mListener;

	public void setListener(Listener listener) {
		if (null != listener) {
			mListener = listener;
		}// end if
	}

	public interface Listener {
		public void finishGetWebsiteCode(boolean success, String result);
	}
}
