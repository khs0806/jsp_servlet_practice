       
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

/* 오라클에서는 rownum을 쓸 때 아래와 같이 조건절을 이용할 수 없다 
이유는 from절이 제일 처음 실행되고 그 다음에 where절이 수행되는데 이때 rownum은
실데이터에선 없는 상황이기 때문에 에러가 난다. 그렇기 때문에 서브쿼리를 이용하여 가져온다.*/
select rownum, board_number, writer, subject from board where rownum = 3;

/*서브쿼리를 이용하여 가능하게 할 수 있다.*/
select a.* from
    (select rownum rnum, board_number, writer, subject from board) a
     where a.rnum = 2;

/*또한 오라클은 까다롭게도 아래처럼 rownum과 *(애스터리스크)를 같이
사용할 수가 없다. 문법구조상 ㅡㅡ;*/
select rownum,* from board order by board_number desc;

/* 서브쿼리를 이용하여 a.*과 같이 사용하면 가능하다. */
select rownum as rnum, a.* from
(select * from board order by board_number desc) a;

select * from
(select rownum as rnum, a.* from
(select * from board order by group_number desc, sequence_number asc) a) b
where b.rnum >= 1 and b.rnum <= 10;

insert into board values(board_board_number_seq.nextval,1,1,1,1,1,sysdate,1,1,1,1);

select * from board;
delete from board;

/* 시퀀스 초기화 */
select board_board_number_seq.nextval from dual;
alter sequence board_board_number_seq increment by 1 minvalue 0;

/* 파일업로드 테이블로 수정 */
alter table board add file_name varchar2(200);
alter table board MODIFY path varchar2(300);
alter table board add file_size number(10);

insert into board(board_number, writer, subject, content, password, write_date, read_count, group_number, sequence_number, sequence_level, file_name, path, file_size) 