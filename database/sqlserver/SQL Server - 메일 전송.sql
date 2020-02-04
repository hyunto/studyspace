USE msdb
GO

DECLARE @date varchar(10);
DECLARE @query NVARCHAR(MAX);
DECLARE @subject VARCHAR(MAX);
DECLARE @body VARCHAR(MAX);

SET @date = CONVERT(NVARCHAR(10), GETDATE() - 1, 120)
SET @query = 'SELECT UserID, LoginDT FROM dbo.NUsers WHERE LoginDT LIKE N'''+ @date + '%'''
				
SET @subject = N'[MXM] ' + CONVERT(VARCHAR(10), @date) + ' 로그인 사용자 리스트'
SET @body = N'MXM NCW Internal Test 중.
자세한 내용은 첨부파일 참고 바랍니다.
문의 사항은 정현수 주임(hyunsoo0813@ncsoft.con) 또는 서비스운영_DB DL로 문의 바랍니다.'

EXEC sp_send_dbmail
	@profile_name = 'Database Mail Profile',	
	@recipients = 'minseokseo@ncsoft.com;zerosum@ncsoft.com;486kbs@ncsoft.com;NCSoft_ServiceManagement2TeamD@ncsoft.com',
	@copy_recipients = 'hyunsoo0813@ncsoft.com',
	@subject = @subject,
	@body = @body,
	@execute_query_database = 'ACCOUNT_GSTAR',
	@query = @query,	
	@attach_query_result_as_file = 1
GO
