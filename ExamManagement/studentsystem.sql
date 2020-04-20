use master
go
if Exists(select * from sysdatabases where name='studentsystem')
	drop database studentsystem
go
create database studentsystem
go
use studentsystem
go
create table [user](
	id int Primary key identity(1,1),
	account varchar(30) not null,
	[password] varchar(30) not null,
	name varchar(20),
	[type] tinyint not null
)
select * from [user]
go
INSERT INTO [user] VALUES ( 'admin', '111111', '����Ա', '1')
,( '2001', '111111', '������', '3')
,( '2005', '111111', '���»�Ŧ����', '3')
,( '2006', '111111', '���˹', '3')
,( '2007', '111111', '����ˮ��', '3')
,( '2008', '111111', '����', '3')
,( '2009', '111111', '��Ͳľ��ҹ', '3')
,( '2010', '111111', '���о�����', '3')
,( '2011', '111111', 'Ϧ�պ�', '3')
,('2012', '111111', 'ӥ���׻���', '3')
,( '201301001', '111111', '����D·��', '2')
,( '201301002', '111111', '�ݿ��ޱ�', '2')
,( '201301003', '111111', '����ŵ��׿��', '2')
,( '201301004', '111111', '���������ǰ�', '2')
select * from [user]
go
create table grade(
	id int primary key identity(1,1),
	name nvarchar(20)
)
go
INSERT INTO grade VALUES ( '2013��'),( '2014��'),( '2015��')
select * from grade
go
create table clazz(
	id int primary key identity(1,1),
	name nvarchar(50) not null,
	gradeid int references grade(id),
)
go
INSERT INTO clazz VALUES ( '3��1��', '1'),( '3��2��', '1')
,( '3��3��', '1'),( '3��4��', '1'),( '4��1��', '2'),( '4��2��', '2')
,( '4��3��', '2'),( '4��4��', '2'),( '5��1��', '3'),( '5��2��', '3')
,( '5��3��', '3'),( '5��4��', '3'),( '5��5��', '3')
select * from clazz
go
create table student( --ѧ����
	id int primary key identity(1,1),
	number nvarchar(20),
	name nvarchar(20),
	sex nvarchar(1),
	phone nvarchar(20),
	qq nvarchar(20),
	photo varchar(20),
	clazzid int references clazz(id)
)
go
  insert into student values ('201301001', '����D·��', '��','18345345612', '252345', 'image/8.jpg', '1')
  ,('201301002', '�ݿ��ޱ�', 'Ů', '16342346345', '345745',  'image/1.jpg', '1')
 ,('201301003', '����ŵ��׿��', '��', '16234574564', '734577',  'image/1.jpg', '1')
 ,( '201301004', '���������ǰ�', '��', '15436574765', '3546634',  'image/1.jpg', '1')
 ,( '201301005', '����', 'Ů', '15346235622', '7453256',  'image/1.jpg', '1')
 ,( '201301006', 'ɽ��', '��', '16234514532', '8456257',  'image/1.jpg', '1')
 ,( '201301007', '��³��', '��', '16345234664', '7257346',  'image/1.jpg', '1')
 ,( '201301008', '������', '��', '16236457676', '8345756',  'image/1.jpg', '1')
 ,( '201301009', '������', '��', '17346734768', '23563457',  'image/1.jpg', '1')
 ,( '201301010', '�ȷ�³����ޱޱ', 'Ů', '11452356234', '235345',  'image/1.jpg', '1')
 ,( '201301011', 'С��', '��', '17632878467', '6235745',  'image/1.jpg', '1')
 ,( '201301012', '÷���', '��', '15235543456', '2352346',  'image/1.jpg', '1')
 ,( '201301013', '�����', '��', '15232342355', '45236',  'image/1.jpg', '1')
 ,( '201302001', '��Ъ������', '��', '13928398784', '89872874',  'image/1.jpg', '2')
 ,( '201302002', '���¿�', '��', '13984728784', '89878372',  'image/1.jpg', '2')
 ,( '201302003', '����˹�ͼ�˹', '��', '13787287843', '99893727',  'image/1.jpg', '2')
 ,( '201302004', '��Q', '��', '18787238748', '89387823',  'image/1.jpg', '2')
 ,( '201302005', '��֮ϣ��', '��', '18398782744', '82094987',  'image/1.jpg', '2')
 ,( '201302006', '��������', 'Ů', '16392784264', '9793845',  'image/1.jpg', '2')
 ,( '201302007', 'ʥ��������', '��', '12787467593', '89874873',  'image/1.jpg', '2')
 ,( '201302008', '�Ϳ�˹����', '��', '15249797297', '89923832',  'image/1.jpg', '2')
 ,( '201302009', '������', '��', '12746763859', '98791235',  'image/1.jpg', '2')
 ,( '201303001', '�����', 'Ů', '15234235688', '12575643',  'image/1.jpg', '3')
 ,( '201303002', 'ɣ�������', 'Ů', '15236386674', '2456568',  'image/1.jpg', '3')
 ,( '201303003', '������³��', 'Ů', '12364573467', '2634681',  'image/1.jpg', '3')
 ,( '201303004', '�������', 'Ů', '16353462367', '23467436',  'image/1.jpg', '3')
 ,( '201303005', '����������', 'Ů', '11345235678', '2352366',  'image/1.jpg', '3')
 ,( '201303006', '��������', 'Ů', '14523462567', '7912635',  'image/1.jpg', '3')
 ,( '201304001', '�׺���', '��', null, null,  'image/1.jpg', '4')
 ,( '201304002', '�����', '��', null, null,  'image/1.jpg', '4')
 ,( '201304003', '��˹', '��', null, null,  'image/1.jpg', '4')
 ,( '201304004', '����', '��', null, null,  'image/1.jpg', '4')
 ,( '201304005', '����', '��', null, null,  'image/1.jpg', '4')
 ,( '201304006', '��˹��', '��', null, null,  'image/1.jpg', '4')
 ,( '201304007', '��������', '��', null, null,  'image/1.jpg', '4')
 ,( '201304008', '����Լ', '��', null, null,  'image/1.jpg', '4')
 ,( '201304009', '������', '��', null, null,  'image/1.jpg', '4')
 ,( '201304010', '���׺�ķ', '��', null, null,  'image/1.jpg', '4')
 ,( '201304011', '��������', '��', null, null,  'image/1.jpg', '4')
 ,( '201304012', '��Ŷ�', '��', null, null,  'image/1.jpg', '4')
 ,( '201304013', '����', '��', null, null,  'image/1.jpg', '4')
 ,( '201304014', '˹�ȶ����', '��', null, null,  'image/1.jpg', '4')
 ,( '201401001', '�������', 'Ů', null, null,  'image/1.jpg', '5')
 ,( '201401002', '�����', '��', null, null,  'image/1.jpg', '5')
 ,( '201401003', '���򻨻�', 'Ů', null, null,  'image/1.jpg', '5')
 ,( '201401004', '����¹��', '��', null, null,  'image/1.jpg', '5')
 ,( '201401005', '��������', '��', null, null,  'image/1.jpg', '5')
 ,( '201401006', '����', '��', null, null,  'image/1.jpg', '5')
 ,( '201401007', 'ɽ�о�Ұ', 'Ů', null, null,  'image/1.jpg', '5')
 ,( '201401008', '�������', '��', null, null,  'image/1.jpg', '5')
 ,( '201401009', 'Ȯڣ��', '��', null, null,  'image/1.jpg', '5')
 ,( '201401010', 'Ұԭ��', 'Ů', null, null,  'image/1.jpg', '5')
 ,( '201401011', '����', 'Ů', null, null,  'image/1.jpg', '5')
 ,( '201401012', 'ľҶ��', '��', null, null,  'image/1.jpg', '5')
 ,( '201401013', '����', '��', null, null,  'image/1.jpg', '5')
 ,( '201401014', '��������', '��', null, null,  'image/1.jpg', '5')
 ,( '201401015', '����', '��', null, null,  'image/1.jpg', '5')
 ,( '201401016', '��Ұӣ', 'Ů', null, null,  'image/1.jpg', '5')
 ,( '201401017', '��Ů־��', '��', null, null,  'image/1.jpg', '5')
 ,( '201402001', '���ǲ�����', '��', null, null,  'image/1.jpg', '6')
 ,( '201402002', '����', '��', null, null,  'image/1.jpg', '6')
 ,( '201402003', '��', '��', null, null,  'image/1.jpg', '6')
 ,( '201402004', '�Ƕ�', '��', null, null, 'image/1.jpg', '6')
 ,( '201402005', '�ϴ���', '��', null, null, 'image/1.jpg', '6')
 ,( '201402006', 'С��', 'Ů', null, null, 'image/1.jpg', '6')
 ,( '201402007', '������', '��', null, null, 'image/1.jpg', '6')
 ,( '201402008', '�ɶ�', '��', null, null, 'image/1.jpg', '6')
 ,( '201402009', 'Ы', '��', '', '', 'image/1.jpg', '6')
 ,( '201402010', '����', '��', null, null, 'image/1.jpg', '6')
 ,( '201402011', 'ǧ������', '��', null, null, 'image/1.jpg', '6')
 select * from student
go
create table teacher(
	id int primary key identity(1,1),
	number nvarchar(20),
	name nvarchar(20),
	sex nvarchar(4),
	phone nvarchar(20),
	qq nvarchar(20),
	photo nvarchar(200)
)
go
 INSERT INTO teacher VALUES ( '2001', '������', '��', '18987831233', '63456345', 'image/1.jpg')
 ,( '2002', '����', '��', '13927387432', '65686786',  'image/1.jpg')
,( '2003', 'ս��', '��', '11389823821', '1233456',  'image/1.jpg')
,( '2004', '����', '��', '15234523454', '7456345',  'image/1.jpg')
,( '2005', '���»�Ŧ����', '��', '16234243242', '34634534',  'image/1.jpg')
,( '2006', '���˹', '��', '16345475689', '35464573',  'image/1.jpg')
,( '2007', '����ˮ��', '��', '15234234234', '35683673',  'image/1.jpg')
,( '2008', '����', 'Ů', '14352341231', '73456236',  'image/1.jpg')
,( '2009', '��Ͳľ��ҹ', 'Ů', '13452342342', '234523455',  'image/1.jpg')
,( '2010', '���о�����', 'Ů', '14423423543', '734562356',  'image/1.jpg')
,( '2011', 'Ϧ�պ�', 'Ů', '15234234523', '7243821',  'image/1.jpg')
,( '2012', 'ӥ���׻���', '��', '15236345346', '8345632',  'image/1.jpg')
select * from teacher
go
create table course(
	id int  primary key identity(1,1),
	name nvarchar(50) not null
)
go
insert into course values('����'),
('��ѧ'),
('Ӣ��'),
('��ѧ'),
('����'),
('����'),
('��ʷ'),
('����'),
('�����'),
('����')
select * from course
go
create table grade_course(
	id int primary Key identity(1,1),
	gradeid int references grade(id),
	courseid int references course(id)
)
go
  INSERT INTO grade_course VALUES ( '1', '1'),
  ( '1', '2') ,
 ( '1', '3') ,
 ( '1', '4') ,
 ( '1', '5') ,
 ( '2', '10') ,
 ( '2', '9') ,
 ( '2', '8') ,
 ( '2', '1') ,
 ('3', '2') ,
 ( '3', '5') ,
 ( '3', '7') ,
 ( '3', '8') 
select * from grade_course
go
create table clazz_course_teacher(
	id int primary key identity(1,1),
	gradeid int references grade(id),
	clazzid int references clazz(id),
	courseid int references grade_course(id),
	teacherid int references teacher(id)
)
go
select * from clazz_course_teacher
INSERT INTO clazz_course_teacher VALUES ( '1', '1', '1', '5'),
( '1','2', '2', '12'),
('2', '3', '3', '11'),
('2', '4', '4', '10'),
('3', '5', '5', '4'),
( '3','6', '6', '9'),
('2', '7', '7', '1'),
('1','8', '8', '8')
select * from clazz_course_teacher

go
create table exam(
	id int primary key identity(1,1),
	name varchar(50) ,
	[time] Date,
	remark varchar(200),
	[type] tinyint default(1),
	gradeid int references grade(id),
	clazzid int references clazz(id),
	courseid int references course(id)
)
SELECT * FROM exam
go
  insert into exam values ( '��ѧ�����λῼ', '2016-01-15', '�������ʦ����Ǽǳɼ�', '1', '1', '1','1')
  ,( '1�����Ĳ���', '2016-01-15', '���嵥Ԫ����', '2', '2', '2','2')
  select * from exam
  go
create table escore(
	id int primary key identity(1,1),
	examid int references exam(id) on delete cascade,
	studentid int references student(id),
	clazzid int references clazz(id),
	grade_courseid int  references grade_course(id),
	score int default(0)
)
go              
INSERT INTO escore VALUES ( '1', '1', '1', '1','123'),
( '1', '2', '1',  '2','56'),
( '1', '3', '1',  '3',  '67'),
( '1', '4', '2',  '2',  '67'),
 ( '1', '5', '2',  '3',  '120'),
 ( '1', '6', '2',  '4',  '76'),
 ( '1', '7', '2',  '5',  '78'),
 ( '1', '8', '3',  '1',  '67'),
 ( '1', '9', '3',  '2',  '87'),
 ( '1', '10', '3',  '3',  '66'),
 ( '1', '11', '3',  '4',  '56'),
 ( '1', '12', '3',  '5',  '88'),
 ('1', '13', '4',  '1',  '89'),
 ( '1', '14', '4',  '2',  '34'),
( '1','15', '4',  '3',  '55'),
( '1', '16', '4',  '4',  '90'),
 ( '1', '17', '4', '5',  '90'),
  ( '2', '18', '1', '1',  '78') ,
  ( '2', '19', '2','1',  '89') ,
  ( '2', '20', '3', '1',  '45') ,
  ( '2', '21', '4','1',   '67') ,
  ( '2', '22', '5',  '1',  '132') ,
  ( '2', '23', '6',  '1',  '123') ,
  ( '2', '24', '7',  '1',  '45') ,
  ( '2', '25', '8',  '1',  '65') ,
  ( '2', '26', '9',  '1',  '78') ,
  ( '2', '27', '10',  '1',  '144') ,
  ( '2', '28', '11',  '1',  '44') ,
  ( '2', '29', '12',  '1',  '65') ,
  ( '2', '30', '13', '1',  '87')
  go
  select * from escore
  go
  create table information(
	id int primary key identity(1,1),
	courseid int references course(id),
  )
