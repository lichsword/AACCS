package org.lichsword.android.ui.view.advance;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

public class DragFileHandler extends TransferHandler {

	private static final long serialVersionUID = 1039632718916239006L;

	private ArrayList<String> cachedDragFilePath = new ArrayList<String>();

	public DragFileHandler() {
		super();
	}

	/**
	 * @param property
	 */
	public DragFileHandler(String property) {
		super(property);
	}

	@Override
	public boolean importData(JComponent comp, Transferable t) {
		try {
			List dragFileList = (List) t
					.getTransferData(DataFlavor.javaFileListFlavor);
			loadDragFiles(dragFileList);
			return true;
		} catch (UnsupportedFlavorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.importData(comp, t);
	}

	private void loadDragFiles(List dragFileList) {
		for (Iterator iter = dragFileList.iterator(); iter.hasNext();) {
			File file = (File) iter.next();
			cachedDragFilePath.add(file.getPath());
		}
	}

	@Override
	public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
		for (int i = 0; i < transferFlavors.length; i++) {
			if (DataFlavor.javaFileListFlavor.equals(transferFlavors[i])) {
				return true;
			}
		}
		return true;
	}

}
