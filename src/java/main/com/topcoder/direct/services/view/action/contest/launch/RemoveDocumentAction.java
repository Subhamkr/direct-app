/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionFileStore;
import com.topcoder.service.studio.UploadedDocument;

/**
 * <p>
 * Removes document.
 * </p>
 * <p>
 * Version 1.1 - Direct - View/Edit/Activate Studio Contests Assembly Change Note - Adds current user into the call
 * </p>
 *
 * @author BeBetter
 * @version 1.1
 */
public class RemoveDocumentAction extends ContestAction {
    /**
     * <p>
     * Contest id.
     * </p>
     */
    private long contestId;

    /**
     * <p>
     * document id.
     * </p>
     */
    private long documentId;

    /**
     * <p>
     * Indicates it is studio or not.
     * </p>
     */
    private boolean studio;

    /**
     * <p>
     * Executes the action to remove the document.
     * </p>
     */
    @Override
    protected void executeAction() throws Exception {
      if(studio) {
          executeActionStudio();
      } else {
          executeActionSoftware();
      }
    }

    /**
     * <p>
     * Executes the action to remove the document.
     * </p>
     */
    private void executeActionSoftware() throws Exception {
        //Gets session file store
        SessionFileStore fileStore = new SessionFileStore(DirectUtils.getServletRequest().getSession(true));

        fileStore.removeFile(documentId);

        setResult(getDocumentResult(documentId));
    }

    /**
     * <p>
     * Executes the action to remove the document.
     * </p>
     */
    private void executeActionStudio() throws Exception {
        if (documentId <= 0) {
            return;
        }

        if (contestId < 0) {
            getContestServiceFacade().removeDocument(null, documentId);
        } else {
            UploadedDocument document = new UploadedDocument();
            document.setContestId(contestId);
            document.setDocumentId(documentId);
            getContestServiceFacade().removeDocumentFromContest(getCurrentUser(), document);
        }

        setResult(getDocumentResult(documentId));
    }

    /**
     * <p>
     * Creates a result map so it could be serialized by JSON serializer.
     * </p>
     *
     * @param document uploaded document
     * @return the result map
     */
    private Map<String, Object> getDocumentResult(long documentId) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("documentId", documentId);
        return result;
    }

    public long getContestId() {
        return contestId;
    }

    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    public long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }

    /**
     * @return the studio
     */
    public boolean isStudio() {
        return studio;
    }

    /**
     * @param studio the studio to set
     */
    public void setStudio(boolean studio) {
        this.studio = studio;
    }


}
