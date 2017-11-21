package ebcs.database.bean;

import java.util.ArrayList;
import java.util.List;

public class FileAuthExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FileAuthExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andAuthIdIsNull() {
            addCriterion("AUTH_ID is null");
            return (Criteria) this;
        }

        public Criteria andAuthIdIsNotNull() {
            addCriterion("AUTH_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAuthIdEqualTo(String value) {
            addCriterion("AUTH_ID =", value, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdNotEqualTo(String value) {
            addCriterion("AUTH_ID <>", value, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdGreaterThan(String value) {
            addCriterion("AUTH_ID >", value, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdGreaterThanOrEqualTo(String value) {
            addCriterion("AUTH_ID >=", value, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdLessThan(String value) {
            addCriterion("AUTH_ID <", value, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdLessThanOrEqualTo(String value) {
            addCriterion("AUTH_ID <=", value, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdLike(String value) {
            addCriterion("AUTH_ID like", value, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdNotLike(String value) {
            addCriterion("AUTH_ID not like", value, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdIn(List<String> values) {
            addCriterion("AUTH_ID in", values, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdNotIn(List<String> values) {
            addCriterion("AUTH_ID not in", values, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdBetween(String value1, String value2) {
            addCriterion("AUTH_ID between", value1, value2, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdNotBetween(String value1, String value2) {
            addCriterion("AUTH_ID not between", value1, value2, "authId");
            return (Criteria) this;
        }

        public Criteria andCustIdIsNull() {
            addCriterion("CUST_ID is null");
            return (Criteria) this;
        }

        public Criteria andCustIdIsNotNull() {
            addCriterion("CUST_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCustIdEqualTo(String value) {
            addCriterion("CUST_ID =", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdNotEqualTo(String value) {
            addCriterion("CUST_ID <>", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdGreaterThan(String value) {
            addCriterion("CUST_ID >", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdGreaterThanOrEqualTo(String value) {
            addCriterion("CUST_ID >=", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdLessThan(String value) {
            addCriterion("CUST_ID <", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdLessThanOrEqualTo(String value) {
            addCriterion("CUST_ID <=", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdLike(String value) {
            addCriterion("CUST_ID like", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdNotLike(String value) {
            addCriterion("CUST_ID not like", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdIn(List<String> values) {
            addCriterion("CUST_ID in", values, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdNotIn(List<String> values) {
            addCriterion("CUST_ID not in", values, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdBetween(String value1, String value2) {
            addCriterion("CUST_ID between", value1, value2, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdNotBetween(String value1, String value2) {
            addCriterion("CUST_ID not between", value1, value2, "custId");
            return (Criteria) this;
        }

        public Criteria andChlCodeIsNull() {
            addCriterion("CHL_CODE is null");
            return (Criteria) this;
        }

        public Criteria andChlCodeIsNotNull() {
            addCriterion("CHL_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andChlCodeEqualTo(String value) {
            addCriterion("CHL_CODE =", value, "chlCode");
            return (Criteria) this;
        }

        public Criteria andChlCodeNotEqualTo(String value) {
            addCriterion("CHL_CODE <>", value, "chlCode");
            return (Criteria) this;
        }

        public Criteria andChlCodeGreaterThan(String value) {
            addCriterion("CHL_CODE >", value, "chlCode");
            return (Criteria) this;
        }

        public Criteria andChlCodeGreaterThanOrEqualTo(String value) {
            addCriterion("CHL_CODE >=", value, "chlCode");
            return (Criteria) this;
        }

        public Criteria andChlCodeLessThan(String value) {
            addCriterion("CHL_CODE <", value, "chlCode");
            return (Criteria) this;
        }

        public Criteria andChlCodeLessThanOrEqualTo(String value) {
            addCriterion("CHL_CODE <=", value, "chlCode");
            return (Criteria) this;
        }

        public Criteria andChlCodeLike(String value) {
            addCriterion("CHL_CODE like", value, "chlCode");
            return (Criteria) this;
        }

        public Criteria andChlCodeNotLike(String value) {
            addCriterion("CHL_CODE not like", value, "chlCode");
            return (Criteria) this;
        }

        public Criteria andChlCodeIn(List<String> values) {
            addCriterion("CHL_CODE in", values, "chlCode");
            return (Criteria) this;
        }

        public Criteria andChlCodeNotIn(List<String> values) {
            addCriterion("CHL_CODE not in", values, "chlCode");
            return (Criteria) this;
        }

        public Criteria andChlCodeBetween(String value1, String value2) {
            addCriterion("CHL_CODE between", value1, value2, "chlCode");
            return (Criteria) this;
        }

        public Criteria andChlCodeNotBetween(String value1, String value2) {
            addCriterion("CHL_CODE not between", value1, value2, "chlCode");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("CREATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("CREATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(String value) {
            addCriterion("CREATE_DATE =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(String value) {
            addCriterion("CREATE_DATE <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(String value) {
            addCriterion("CREATE_DATE >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_DATE >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(String value) {
            addCriterion("CREATE_DATE <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(String value) {
            addCriterion("CREATE_DATE <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLike(String value) {
            addCriterion("CREATE_DATE like", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotLike(String value) {
            addCriterion("CREATE_DATE not like", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<String> values) {
            addCriterion("CREATE_DATE in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<String> values) {
            addCriterion("CREATE_DATE not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(String value1, String value2) {
            addCriterion("CREATE_DATE between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(String value1, String value2) {
            addCriterion("CREATE_DATE not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("CREATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("CREATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(String value) {
            addCriterion("CREATE_TIME =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(String value) {
            addCriterion("CREATE_TIME <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(String value) {
            addCriterion("CREATE_TIME >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_TIME >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(String value) {
            addCriterion("CREATE_TIME <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(String value) {
            addCriterion("CREATE_TIME <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLike(String value) {
            addCriterion("CREATE_TIME like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotLike(String value) {
            addCriterion("CREATE_TIME not like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<String> values) {
            addCriterion("CREATE_TIME in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<String> values) {
            addCriterion("CREATE_TIME not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(String value1, String value2) {
            addCriterion("CREATE_TIME between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(String value1, String value2) {
            addCriterion("CREATE_TIME not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andFinishDateIsNull() {
            addCriterion("FINISH_DATE is null");
            return (Criteria) this;
        }

        public Criteria andFinishDateIsNotNull() {
            addCriterion("FINISH_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andFinishDateEqualTo(String value) {
            addCriterion("FINISH_DATE =", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateNotEqualTo(String value) {
            addCriterion("FINISH_DATE <>", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateGreaterThan(String value) {
            addCriterion("FINISH_DATE >", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateGreaterThanOrEqualTo(String value) {
            addCriterion("FINISH_DATE >=", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateLessThan(String value) {
            addCriterion("FINISH_DATE <", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateLessThanOrEqualTo(String value) {
            addCriterion("FINISH_DATE <=", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateLike(String value) {
            addCriterion("FINISH_DATE like", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateNotLike(String value) {
            addCriterion("FINISH_DATE not like", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateIn(List<String> values) {
            addCriterion("FINISH_DATE in", values, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateNotIn(List<String> values) {
            addCriterion("FINISH_DATE not in", values, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateBetween(String value1, String value2) {
            addCriterion("FINISH_DATE between", value1, value2, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateNotBetween(String value1, String value2) {
            addCriterion("FINISH_DATE not between", value1, value2, "finishDate");
            return (Criteria) this;
        }

        public Criteria andHashKeyIsNull() {
            addCriterion("HASH_KEY is null");
            return (Criteria) this;
        }

        public Criteria andHashKeyIsNotNull() {
            addCriterion("HASH_KEY is not null");
            return (Criteria) this;
        }

        public Criteria andHashKeyEqualTo(String value) {
            addCriterion("HASH_KEY =", value, "hashKey");
            return (Criteria) this;
        }

        public Criteria andHashKeyNotEqualTo(String value) {
            addCriterion("HASH_KEY <>", value, "hashKey");
            return (Criteria) this;
        }

        public Criteria andHashKeyGreaterThan(String value) {
            addCriterion("HASH_KEY >", value, "hashKey");
            return (Criteria) this;
        }

        public Criteria andHashKeyGreaterThanOrEqualTo(String value) {
            addCriterion("HASH_KEY >=", value, "hashKey");
            return (Criteria) this;
        }

        public Criteria andHashKeyLessThan(String value) {
            addCriterion("HASH_KEY <", value, "hashKey");
            return (Criteria) this;
        }

        public Criteria andHashKeyLessThanOrEqualTo(String value) {
            addCriterion("HASH_KEY <=", value, "hashKey");
            return (Criteria) this;
        }

        public Criteria andHashKeyLike(String value) {
            addCriterion("HASH_KEY like", value, "hashKey");
            return (Criteria) this;
        }

        public Criteria andHashKeyNotLike(String value) {
            addCriterion("HASH_KEY not like", value, "hashKey");
            return (Criteria) this;
        }

        public Criteria andHashKeyIn(List<String> values) {
            addCriterion("HASH_KEY in", values, "hashKey");
            return (Criteria) this;
        }

        public Criteria andHashKeyNotIn(List<String> values) {
            addCriterion("HASH_KEY not in", values, "hashKey");
            return (Criteria) this;
        }

        public Criteria andHashKeyBetween(String value1, String value2) {
            addCriterion("HASH_KEY between", value1, value2, "hashKey");
            return (Criteria) this;
        }

        public Criteria andHashKeyNotBetween(String value1, String value2) {
            addCriterion("HASH_KEY not between", value1, value2, "hashKey");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNull() {
            addCriterion("FILE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNotNull() {
            addCriterion("FILE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andFileNameEqualTo(String value) {
            addCriterion("FILE_NAME =", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotEqualTo(String value) {
            addCriterion("FILE_NAME <>", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThan(String value) {
            addCriterion("FILE_NAME >", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("FILE_NAME >=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThan(String value) {
            addCriterion("FILE_NAME <", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThanOrEqualTo(String value) {
            addCriterion("FILE_NAME <=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLike(String value) {
            addCriterion("FILE_NAME like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotLike(String value) {
            addCriterion("FILE_NAME not like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameIn(List<String> values) {
            addCriterion("FILE_NAME in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotIn(List<String> values) {
            addCriterion("FILE_NAME not in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameBetween(String value1, String value2) {
            addCriterion("FILE_NAME between", value1, value2, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotBetween(String value1, String value2) {
            addCriterion("FILE_NAME not between", value1, value2, "fileName");
            return (Criteria) this;
        }

        public Criteria andFilePathIsNull() {
            addCriterion("FILE_PATH is null");
            return (Criteria) this;
        }

        public Criteria andFilePathIsNotNull() {
            addCriterion("FILE_PATH is not null");
            return (Criteria) this;
        }

        public Criteria andFilePathEqualTo(String value) {
            addCriterion("FILE_PATH =", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotEqualTo(String value) {
            addCriterion("FILE_PATH <>", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathGreaterThan(String value) {
            addCriterion("FILE_PATH >", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("FILE_PATH >=", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLessThan(String value) {
            addCriterion("FILE_PATH <", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLessThanOrEqualTo(String value) {
            addCriterion("FILE_PATH <=", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLike(String value) {
            addCriterion("FILE_PATH like", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotLike(String value) {
            addCriterion("FILE_PATH not like", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathIn(List<String> values) {
            addCriterion("FILE_PATH in", values, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotIn(List<String> values) {
            addCriterion("FILE_PATH not in", values, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathBetween(String value1, String value2) {
            addCriterion("FILE_PATH between", value1, value2, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotBetween(String value1, String value2) {
            addCriterion("FILE_PATH not between", value1, value2, "filePath");
            return (Criteria) this;
        }

        public Criteria andTotalSizeIsNull() {
            addCriterion("TOTAL_SIZE is null");
            return (Criteria) this;
        }

        public Criteria andTotalSizeIsNotNull() {
            addCriterion("TOTAL_SIZE is not null");
            return (Criteria) this;
        }

        public Criteria andTotalSizeEqualTo(Long value) {
            addCriterion("TOTAL_SIZE =", value, "totalSize");
            return (Criteria) this;
        }

        public Criteria andTotalSizeNotEqualTo(Long value) {
            addCriterion("TOTAL_SIZE <>", value, "totalSize");
            return (Criteria) this;
        }

        public Criteria andTotalSizeGreaterThan(Long value) {
            addCriterion("TOTAL_SIZE >", value, "totalSize");
            return (Criteria) this;
        }

        public Criteria andTotalSizeGreaterThanOrEqualTo(Long value) {
            addCriterion("TOTAL_SIZE >=", value, "totalSize");
            return (Criteria) this;
        }

        public Criteria andTotalSizeLessThan(Long value) {
            addCriterion("TOTAL_SIZE <", value, "totalSize");
            return (Criteria) this;
        }

        public Criteria andTotalSizeLessThanOrEqualTo(Long value) {
            addCriterion("TOTAL_SIZE <=", value, "totalSize");
            return (Criteria) this;
        }

        public Criteria andTotalSizeIn(List<Long> values) {
            addCriterion("TOTAL_SIZE in", values, "totalSize");
            return (Criteria) this;
        }

        public Criteria andTotalSizeNotIn(List<Long> values) {
            addCriterion("TOTAL_SIZE not in", values, "totalSize");
            return (Criteria) this;
        }

        public Criteria andTotalSizeBetween(Long value1, Long value2) {
            addCriterion("TOTAL_SIZE between", value1, value2, "totalSize");
            return (Criteria) this;
        }

        public Criteria andTotalSizeNotBetween(Long value1, Long value2) {
            addCriterion("TOTAL_SIZE not between", value1, value2, "totalSize");
            return (Criteria) this;
        }

        public Criteria andPieceSizeIsNull() {
            addCriterion("PIECE_SIZE is null");
            return (Criteria) this;
        }

        public Criteria andPieceSizeIsNotNull() {
            addCriterion("PIECE_SIZE is not null");
            return (Criteria) this;
        }

        public Criteria andPieceSizeEqualTo(Long value) {
            addCriterion("PIECE_SIZE =", value, "pieceSize");
            return (Criteria) this;
        }

        public Criteria andPieceSizeNotEqualTo(Long value) {
            addCriterion("PIECE_SIZE <>", value, "pieceSize");
            return (Criteria) this;
        }

        public Criteria andPieceSizeGreaterThan(Long value) {
            addCriterion("PIECE_SIZE >", value, "pieceSize");
            return (Criteria) this;
        }

        public Criteria andPieceSizeGreaterThanOrEqualTo(Long value) {
            addCriterion("PIECE_SIZE >=", value, "pieceSize");
            return (Criteria) this;
        }

        public Criteria andPieceSizeLessThan(Long value) {
            addCriterion("PIECE_SIZE <", value, "pieceSize");
            return (Criteria) this;
        }

        public Criteria andPieceSizeLessThanOrEqualTo(Long value) {
            addCriterion("PIECE_SIZE <=", value, "pieceSize");
            return (Criteria) this;
        }

        public Criteria andPieceSizeIn(List<Long> values) {
            addCriterion("PIECE_SIZE in", values, "pieceSize");
            return (Criteria) this;
        }

        public Criteria andPieceSizeNotIn(List<Long> values) {
            addCriterion("PIECE_SIZE not in", values, "pieceSize");
            return (Criteria) this;
        }

        public Criteria andPieceSizeBetween(Long value1, Long value2) {
            addCriterion("PIECE_SIZE between", value1, value2, "pieceSize");
            return (Criteria) this;
        }

        public Criteria andPieceSizeNotBetween(Long value1, Long value2) {
            addCriterion("PIECE_SIZE not between", value1, value2, "pieceSize");
            return (Criteria) this;
        }

        public Criteria andPieceCountIsNull() {
            addCriterion("PIECE_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andPieceCountIsNotNull() {
            addCriterion("PIECE_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andPieceCountEqualTo(Long value) {
            addCriterion("PIECE_COUNT =", value, "pieceCount");
            return (Criteria) this;
        }

        public Criteria andPieceCountNotEqualTo(Long value) {
            addCriterion("PIECE_COUNT <>", value, "pieceCount");
            return (Criteria) this;
        }

        public Criteria andPieceCountGreaterThan(Long value) {
            addCriterion("PIECE_COUNT >", value, "pieceCount");
            return (Criteria) this;
        }

        public Criteria andPieceCountGreaterThanOrEqualTo(Long value) {
            addCriterion("PIECE_COUNT >=", value, "pieceCount");
            return (Criteria) this;
        }

        public Criteria andPieceCountLessThan(Long value) {
            addCriterion("PIECE_COUNT <", value, "pieceCount");
            return (Criteria) this;
        }

        public Criteria andPieceCountLessThanOrEqualTo(Long value) {
            addCriterion("PIECE_COUNT <=", value, "pieceCount");
            return (Criteria) this;
        }

        public Criteria andPieceCountIn(List<Long> values) {
            addCriterion("PIECE_COUNT in", values, "pieceCount");
            return (Criteria) this;
        }

        public Criteria andPieceCountNotIn(List<Long> values) {
            addCriterion("PIECE_COUNT not in", values, "pieceCount");
            return (Criteria) this;
        }

        public Criteria andPieceCountBetween(Long value1, Long value2) {
            addCriterion("PIECE_COUNT between", value1, value2, "pieceCount");
            return (Criteria) this;
        }

        public Criteria andPieceCountNotBetween(Long value1, Long value2) {
            addCriterion("PIECE_COUNT not between", value1, value2, "pieceCount");
            return (Criteria) this;
        }

        public Criteria andUploadTypeIsNull() {
            addCriterion("UPLOAD_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andUploadTypeIsNotNull() {
            addCriterion("UPLOAD_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andUploadTypeEqualTo(String value) {
            addCriterion("UPLOAD_TYPE =", value, "uploadType");
            return (Criteria) this;
        }

        public Criteria andUploadTypeNotEqualTo(String value) {
            addCriterion("UPLOAD_TYPE <>", value, "uploadType");
            return (Criteria) this;
        }

        public Criteria andUploadTypeGreaterThan(String value) {
            addCriterion("UPLOAD_TYPE >", value, "uploadType");
            return (Criteria) this;
        }

        public Criteria andUploadTypeGreaterThanOrEqualTo(String value) {
            addCriterion("UPLOAD_TYPE >=", value, "uploadType");
            return (Criteria) this;
        }

        public Criteria andUploadTypeLessThan(String value) {
            addCriterion("UPLOAD_TYPE <", value, "uploadType");
            return (Criteria) this;
        }

        public Criteria andUploadTypeLessThanOrEqualTo(String value) {
            addCriterion("UPLOAD_TYPE <=", value, "uploadType");
            return (Criteria) this;
        }

        public Criteria andUploadTypeLike(String value) {
            addCriterion("UPLOAD_TYPE like", value, "uploadType");
            return (Criteria) this;
        }

        public Criteria andUploadTypeNotLike(String value) {
            addCriterion("UPLOAD_TYPE not like", value, "uploadType");
            return (Criteria) this;
        }

        public Criteria andUploadTypeIn(List<String> values) {
            addCriterion("UPLOAD_TYPE in", values, "uploadType");
            return (Criteria) this;
        }

        public Criteria andUploadTypeNotIn(List<String> values) {
            addCriterion("UPLOAD_TYPE not in", values, "uploadType");
            return (Criteria) this;
        }

        public Criteria andUploadTypeBetween(String value1, String value2) {
            addCriterion("UPLOAD_TYPE between", value1, value2, "uploadType");
            return (Criteria) this;
        }

        public Criteria andUploadTypeNotBetween(String value1, String value2) {
            addCriterion("UPLOAD_TYPE not between", value1, value2, "uploadType");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("STATE is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("STATE is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(String value) {
            addCriterion("STATE =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(String value) {
            addCriterion("STATE <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(String value) {
            addCriterion("STATE >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(String value) {
            addCriterion("STATE >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(String value) {
            addCriterion("STATE <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(String value) {
            addCriterion("STATE <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLike(String value) {
            addCriterion("STATE like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotLike(String value) {
            addCriterion("STATE not like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<String> values) {
            addCriterion("STATE in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<String> values) {
            addCriterion("STATE not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(String value1, String value2) {
            addCriterion("STATE between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(String value1, String value2) {
            addCriterion("STATE not between", value1, value2, "state");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}