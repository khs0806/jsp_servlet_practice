       
create table board(
    board_number number(8) not null,
    writer varchar2(10) not null,
    subject varchar2(100) not null,
    email varchar2(50) not null,
    content varchar2(4000) not null,
    password varchar2(20) not null,
    
    write_date date not null,
    read_count number(5) default 0,
    group_number number(5) not null,
    sequence_number number(5) not null,
    sequence_level number(5) not null,
    primary key(board_number)
);

create sequence board_board_number_seq;

select max(group_number) from board;

select * from board;

select board_number, writer, subject from board;
select rownum, board_number, writer, subject from board;

/* ����Ŭ������ rownum�� �� �� �Ʒ��� ���� �������� �̿��� �� ���� 
������ from���� ���� ó�� ����ǰ� �� ������ where���� ����Ǵµ� �̶� rownum��
�ǵ����Ϳ��� ���� ��Ȳ�̱� ������ ������ ����. �׷��� ������ ���������� �̿��Ͽ� �����´�.*/
select rownum, board_number, writer, subject from board where rownum = 3;

/*���������� �̿��Ͽ� �����ϰ� �� �� �ִ�.*/
select a.* from
    (select rownum rnum, board_number, writer, subject from board) a
     where a.rnum = 2;

/*���� ����Ŭ�� ��ٷӰԵ� �Ʒ�ó�� rownum�� *(�ֽ��͸���ũ)�� ����
����� ���� ����. ���������� �Ѥ�;*/
select rownum,* from board order by board_number desc;

/* ���������� �̿��Ͽ� a.*�� ���� ����ϸ� �����ϴ�. */
select rownum as rnum, a.* from
(select * from board order by board_number desc) a;

select * from
(select rownum as rnum, a.* from
(select * from board order by group_number desc, sequence_number asc) a) b
where b.rnum >= 1 and b.rnum <= 10;

insert into board values(board_board_number_seq.nextval,1,1,1,1,1,sysdate,1,1,1,1);

select * from board;
delete from board;

/* ������ �ʱ�ȭ */
select board_board_number_seq.nextval from dual;
alter sequence board_board_number_seq increment by 1 minvalue 0;

/* ���Ͼ��ε� ���̺�� ���� */
alter table board add file_name varchar2(200);
alter table board MODIFY path varchar2(300);
alter table board add file_size number(10);

insert into board(board_number, writer, subject, content, password, write_date, read_count, group_number, sequence_number, sequence_level, file_name, path, file_size) 