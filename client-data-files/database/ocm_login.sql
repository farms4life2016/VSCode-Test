USE [master]
GO

/****** Object:  Login [ocm]    Script Date: 2022-02-06 9:23:50 PM ******/
CREATE LOGIN [ocm] WITH PASSWORD=N'p123456', DEFAULT_DATABASE=[master], DEFAULT_LANGUAGE=[us_english], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF
GO


ALTER SERVER ROLE [sysadmin] ADD MEMBER [ocm]
GO

