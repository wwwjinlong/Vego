package ebcs.database.bean;

public class FilePiece extends FilePieceKey {
    private String createDate;

    private String createTime;

    private String finishTime;

    private String saveType;

    private String serverIp;

    private String filePath;

    private String saveKey;

    private Long pieceSize;

    private String state;

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

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime == null ? null : finishTime.trim();
    }

    public String getSaveType() {
        return saveType;
    }

    public void setSaveType(String saveType) {
        this.saveType = saveType == null ? null : saveType.trim();
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp == null ? null : serverIp.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getSaveKey() {
        return saveKey;
    }

    public void setSaveKey(String saveKey) {
        this.saveKey = saveKey == null ? null : saveKey.trim();
    }

    public Long getPieceSize() {
        return pieceSize;
    }

    public void setPieceSize(Long pieceSize) {
        this.pieceSize = pieceSize;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}