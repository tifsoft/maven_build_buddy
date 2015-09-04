package com.tifsoft.mavenbuildbuddy.gui;

import javax.swing.JTextPane;
import javax.swing.SizeRequirements;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.GlyphView;
import javax.swing.text.ParagraphView;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.InlineView;

public class CustomTextPane extends JTextPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String SEPARATOR = System.getProperty("line.separator");
	private HTMLEditorKit kit;

	CustomTextPane() {
		// private String SEPARATOR = System.getProperty("line.separator");

		this.kit = new HTMLEditorKit() {
			/**
			* 
			*/
			private static final long serialVersionUID = 1L;

			@Override
			public ViewFactory getViewFactory() {

				return new HTMLFactory() {
					@Override
					public View create(final Element e) {
						final View v = super.create(e);
						if (v instanceof InlineView) {
							return new InlineView(e) {
								@Override
								public int getBreakWeight(final int axis, final float pos, final float len) {
									// return GoodBreakWeight;
									if (axis == View.X_AXIS) {
										checkPainter();
										final int p0 = getStartOffset();
										final int p1 = getGlyphPainter().getBoundedPosition(this, p0, pos, len);
										if (p1 == p0) {
											// can't even fit a single character
											return View.BadBreakWeight;
										}
										try {
											// if the view contains line break
											// char return forced break
											if (getDocument().getText(p0, p1 - p0)
													.indexOf(CustomTextPane.this.SEPARATOR) >= 0) {
												return View.ForcedBreakWeight;
											}
										} catch (final BadLocationException ex) {
											// should never happen
										}

									}
									return super.getBreakWeight(axis, pos, len);
								}

								@Override
								public View breakView(final int axis, final int p0, final float pos, final float len) {
									if (axis == View.X_AXIS) {
										checkPainter();
										final int p1 = getGlyphPainter().getBoundedPosition(this, p0, pos, len);
										try {
											// if the view contains line break
											// char break the view
											final int index = getDocument().getText(p0, p1 - p0)
													.indexOf(CustomTextPane.this.SEPARATOR);
											if (index >= 0) {
												final GlyphView v = (GlyphView) createFragment(p0, p0 + index + 1);
												return v;
											}
										} catch (final BadLocationException ex) {
											// should never happen
										}

									}
									return super.breakView(axis, p0, pos, len);
								}
							};
						} else if (v instanceof ParagraphView) {
							return new ParagraphView(e) {
								@Override
								protected SizeRequirements calculateMinorAxisRequirements(final int axis,
										SizeRequirements r) {
									if (r == null) {
										r = new SizeRequirements();
									}
									final float pref = this.layoutPool.getPreferredSpan(axis);
									final float min = this.layoutPool.getMinimumSpan(axis);
									// Don't include insets, Box.getXXXSpan will
									// include them.
									r.minimum = (int) min;
									r.preferred = Math.max(r.minimum, (int) pref);
									r.maximum = Integer.MAX_VALUE;
									r.alignment = 0.5f;
									return r;
								}

							};
						}
						return v;
					}
				};
			}
		};
		
		this.setEditorKit(this.kit);
	}
}