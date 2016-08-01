#인덱스 관련 DMV

##가장 조각이 많이 난 인덱스 TOP 10
```SQL
SELECT TOP 10
	DB_NAME() AS 'DatabaseName',
    OBJECT_NAME(s.[object_id]) AS 'TableName',
    i.name AS 'IndexName',
    ROUND(avg_fragmentation_in_percent, 2) AS 'Fragmentation %'
FROM sys.dm_db_index_physical_stats(db_id(), NULL, NULL, NULL, NULL) AS s
	INNER JOIN sys.indexes AS i
    ON s.object_id = i.obejct_id AND s.index_id = i.index_id
WHERE s.database_id = DB_ID()	-- 현재 데이터베이스
	AND i.name IS NOT NULL	-- HEAP은 무시
    AND OBJECTPROPERTY(s.[object_id], 'IsMsShipped') = 0	-- 시스템 개체 무시
ORDER BY [Fragmentation %] DESC
GO
```

##Find Missing Indexes

```SQL
-- Missing Indexes in current database by Index Advantage
SELECT 
	user_seeks * avg_total_user_cost * (avg_user_impact * 0.01) AS [index_advantage],
    migs.last_user_seek,
    mid.[statement] AS [Database.Schema.Table],
    mid.equality_columns,
    mid.inequality_columns,
    mid.included_columns,
    migs.unique_compiles,
    migs.user_seeks,
    migs.avg_total_user_cost,
    migs.avg_user_impact
FROM sys.dm_db_missing_index_group_stats AS migs WITH (NOLOCK)
	INNER JOIN sys.dm_db_missing_index_groups AS mig WITH (NOLOCK)
    ON migs.group_handle = mig.index_group_handle
    INNER JOIN sys.dm_db_missing_index_details AS mid WITH (NOLOCK)
    ON mig.index_handle = mid.index_handle
WHERE mid.database_id = DB_ID()
ORDER BY index_advantage DESC;

```