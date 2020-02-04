-- 서버 인스턴스에서 상위 유형의 대기만을 선별
WITH Waits AS
(SELECT wait_type
		, wait_time_ms / 1000. AS wait_time_s
		, 100. * wait_time_ms / SUM(wait_time_ms) OVER() AS pct
		, ROW_NUMBER() OVER(ORDER BY wait_time_ms DESC) AS rn
	FROM sys.dm_os_wait_stats
	WHERE wait_type NOT IN('SLEEP_TASK', 'BROKER_TASK_STOP'
		, 'SQLTRACE_BUFFER_FLUSH', 'CLR_AUTO_EVENT'
		, 'CLR_MANUAL_EVENT', 'LAZYWRITER_SLEEP') -- 중요하지 않은 대기 유형은 분석 결과에서 제외
)
SELECT W1.wait_type
		, CAST(W1.wait_time_s AS DECIMAL(12, 2)) AS wait_time_s
		, CAST(W1.pct AS DECIMAL(12, 2)) AS pct
		, CAST(SUM(W2.pct) AS DECIMAL(12, 2)) AS rummint_pct
	FROM Waits AS W1
	INNER JOIN Waits AS W2
	ON W2.rn <= W1.rn
	GROUP BY W1.rn, W1.wait_type, W1.wait_time_s, W1.pct
	HAVING SUM(W2.pct) - W1.pct < 95  -- 퍼센트 임계치
GO