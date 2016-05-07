GO

-- ----------------------------
-- Table structure for tb_customer
-- ----------------------------
DROP TABLE [tb_customer]
GO
CREATE TABLE [tb_customer] (
[Id] int NULL ,
[firstName] varchar(255) NULL ,
[lastName] varchar(255) NULL 
)


GO

-- ----------------------------
-- Records of tb_customer
-- ----------------------------
BEGIN TRANSACTION
GO
INSERT INTO [tb_customer] ([Id], [firstName], [lastName]) VALUES (N'1', N'A', N'A'), (N'2', N'B', N'B'), (N'3', N'C', N'C'), (N'4', N'D', N'D'), (N'5', N'E', N'E'), (N'6', N'F', N'F'), (N'7', N'G', N'G'), (N'8', N'H', N'H'), (N'10', N'Ca', N'Ca'), (N'11', N'Cax', N'Cax')
GO
GO
COMMIT TRANSACTION
GO

-- ----------------------------
-- Table structure for tb_insurance
-- ----------------------------
DROP TABLE [tb_insurance]
GO
CREATE TABLE [tb_insurance] (
[Id] int NULL ,
[customerId] int NULL ,
[modelId] int NULL ,
[dateFrom] datetime2 NULL ,
[dateTo] datetime2 NULL 
)


GO

-- ----------------------------
-- Records of tb_insurance
-- ----------------------------
BEGIN TRANSACTION
GO
INSERT INTO [tb_insurance] ([Id], [customerId], [modelId], [dateFrom], [dateTo]) VALUES (N'1', N'1', N'1', '2014-01-01 00:00:00', '2015-06-30 00:00:00'), (N'2', N'1', N'1', '2014-01-01 00:00:00', '2015-06-30 00:00:00'), (N'3', N'1', N'1', '2014-01-01 00:00:00', '2015-06-30 00:00:00'), (N'4', N'1', N'1', '2014-01-01 00:00:00', '2015-06-30 00:00:00'), (N'5', N'1', N'1', '2014-01-01 00:00:00', '2015-06-30 00:00:00'), (N'6', N'1', N'1', '2014-01-01 00:00:00', '2015-06-30 00:00:00'), (N'7', N'1', N'1', '2014-01-01 00:00:00', '2015-06-30 00:00:00'), (N'8', N'1', N'1', '2014-01-01 00:00:00', '2014-01-02 00:00:00'), (N'10', N'10', N'1', '2014-01-01 00:00:00', '2014-12-30 00:00:00'), (N'11', N'11', N'1', '2014-01-01 00:00:00', '2014-12-30 00:00:00')
GO
GO
COMMIT TRANSACTION
GO

-- ----------------------------
-- Table structure for tb_model
-- ----------------------------
DROP TABLE [tb_model]
GO
CREATE TABLE [tb_model] (
[Id] int NULL ,
[model] varchar(255) NULL 
)


GO

-- ----------------------------
-- Records of tb_model
-- ----------------------------
BEGIN TRANSACTION
GO
INSERT INTO [tb_model] ([Id], [model]) VALUES (N'1', N'Ford Mondeo'), (N'2', N'Fiat 500'), (N'3', N'Mercedes C320'), (N'4', N'Ford Focus')
GO
GO
COMMIT TRANSACTION
GO


-- STARE
-- create table Tb_Customer(
	-- Id int not null primary key,
	-- firstName nvarchar(50),
	-- lastName nvarchar(50)
-- );

-- Create Table Tb_Model(
	-- Id int not null primary key,
	-- model nvarchar(50)
-- );

-- CREATE TABLE TB_Insurance (
	-- Id int not null primary key,
	-- customerId int not null foreign key references Tb_Customer(Id),
	-- modelId int not null foreign key references TB_model(id),
	-- dateFrom datetime not null,
	-- dateTo datetime not null,
-- );
