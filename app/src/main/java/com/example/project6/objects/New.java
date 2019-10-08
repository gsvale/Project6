package com.example.project6.objects;

/**
 * The type New.
 */
public class New {


    private String mNewTitle;
    private String mNewSection;
    private String mNewAuthor;
    private String mNewDate;
    private String mNewUrl;

    /**
     * Instantiates a new New.
     *
     * @param newTitle    the new title
     * @param newSecttion the new secttion
     * @param newAuthor   the new author
     * @param newDate     the new date
     * @param newUrl      the new url
     */
    public New(String newTitle, String newSecttion, String newAuthor, String newDate, String newUrl) {
        this.mNewTitle = newTitle;
        this.mNewSection = newSecttion;
        this.mNewAuthor = newAuthor;
        this.mNewDate = newDate;
        this.mNewUrl = newUrl;
    }

    /**
     * Gets new title.
     *
     * @return the new title
     */
    public String getNewTitle() {
        return mNewTitle;
    }

    /**
     * Gets new section.
     *
     * @return the new section
     */
    public String getNewSection() {
        return mNewSection;
    }

    /**
     * Gets new author.
     *
     * @return the new author
     */
    public String getNewAuthor() {
        return mNewAuthor;
    }

    /**
     * Gets new date.
     *
     * @return the new date
     */
    public String getNewDate() {
        return mNewDate;
    }

    /**
     * Gets new url.
     *
     * @return the new url
     */
    public String getNewUrl() {
        return mNewUrl;
    }
}
