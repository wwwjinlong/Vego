package ebcs.database.bean;

public class FileAuth {
    private String authId;

    private String custId;

    private String chlCode;

    private String createDate;

    private String createTime;

    private String finishDate;

    private String hashKey;

    private String fileName;

    private String filePath;

    private Long totalSize;

    private Long pieceSize;

    private Long pieceCount;

    private String uploadType;

    private String state;

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId == null ? null : authId.trim();
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public String getChlCode() {
        return chlCode;
    }

    public void setChlCode(String chlCode) {
        this.chlCode = chlCode == null ? null : chlCode.trim();
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate == null ? null : createDate.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate == null ? null : finishDate.trim();
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey == null ? null : hashKey.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public Long getPieceSize() {
        return pieceSize;
    }

    public void setPieceSize(Long pieceSize) {
        this.pieceSize = pieceSize;
    }

    public Long getPieceCount() {
        return pieceCount;
    }

    public void setPieceCount(Long pieceCount) {
        this.pieceCount = pieceCount;
    }

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType == null ? null : uploadType.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}