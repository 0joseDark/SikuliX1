package org.sikuli.script.support.gui;

import org.apache.commons.io.FilenameUtils;
import org.sikuli.ide.EditorImageButton;
import org.sikuli.ide.SikulixIDE;
import org.sikuli.script.Region;
import org.sikuli.script.SX;
import org.sikuli.script.support.devices.ScreenDevice;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class SXDialogPaneImage extends SXDialogIDE {
  public SXDialogPaneImage(Point where, Object... parms) {
    super("sxidepaneimage", where, parms);
  }

  public SXDialogPaneImage(String res, Point where, Object... parms) {
    super(res, where, parms);
  }

  File image = (File) getOptions().get("image");
  BufferedImage scrImage = null;
  Rectangle ideWindow = null;

  private void prepare() {
    final SikulixIDE sxide = SikulixIDE.get();
    ideWindow = sxide.getBounds();
    final ScreenDevice scr = ScreenDevice.getScreenForPoint(ideWindow.getLocation());
    SikulixIDE.doHide();
    scrImage = scr.capture();
    scrImage = new ImageItem(scrImage).resize((int) ideWindow.getWidth()).get();
  }

  public void rename() {
    closeCancel();
    final String image = FilenameUtils.getBaseName(((File) getOptions().get("image")).getAbsolutePath());
    final Region showAt = new Region(getLocation().x, getLocation().y, 1, 1);
    final String name = SX.input("New name for image " + image, "ImageButton :: rename", showAt);
    EditorImageButton.renameImage(name, getOptions());
  }

  public void optimize() {
    closeCancel();
    prepare();
    new SXDialogPaneImageOptimize(ideWindow.getLocation(), new String[]{"image", "shot"},
        image, scrImage).run();
  }

  public void pattern() {
    closeCancel();
    prepare();
    new SXDialogPaneImagePattern(ideWindow.getLocation(), new String[]{"image", "shot", "pattern"},
        getOptions().get("image"), scrImage).run();
  }
}
