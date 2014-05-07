/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.game;

/**
 *
 * @author Zsolt
 */
public class SimplePiece implements Piece{
    protected boolean isClickable;
    protected String cssClass;
    protected String styleOverride;
    protected String content;

    protected String textColor;
    protected String backgroundColor;

    public SimplePiece() {
    }

    public SimplePiece(boolean isClickable, String cssClass, String styleOverride, String content, String textColor, String backgroundColor) {
        this.isClickable = isClickable;
        this.cssClass = cssClass;
        this.styleOverride = styleOverride;
        this.content = content;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
    }
    
    @Override
    public boolean isClickable() {
        return isClickable;
    }

    @Override
    public String getCssClass() {
        return cssClass + (isClickable ? " clickable" : "");
    }

    @Override
    public String getStyleOverride() {
        return 
                (textColor == null ? "" : "color: #" + textColor) +
                (backgroundColor == null ? "" : "background-color: #" + backgroundColor) +
                styleOverride;
    }

    @Override
    public String getContent() {
        return content;
    }

    public void setIsClickable(boolean isClickable) {
        this.isClickable = isClickable;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public void setStyleOverride(String styleOverride) {
        this.styleOverride = styleOverride;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    
    
    
    
    
}
