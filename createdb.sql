CREATE TABLE yelpuser
(   
   UName VARCHAR2(255),
   UserId VARCHAR2(50),
   UType VARCHAR2(10),
   YelpingSince date,
   FunnyVotes NUMBER,
   UsefulVotes NUMBER,
   CoolVotes NUMBER,
   ReviewCount NUMBER,
   Fans NUMBER,
   AvgStars FLOAT,
Compliments VARCHAR2(255),
   PRIMARY KEY (UserId)
);

CREATE TABLE Friends
(
   UserId VARCHAR2(30),
   FriendId VARCHAR2(30),
   PRIMARY KEY (UserId,FriendId),
   FOREIGN  KEY (UserId) REFERENCES yelpuser (UserId) ON DELETE CASCADE
);

CREATE TABLE Business
(
   BId VARCHAR2(50),
   BType VARCHAR2(50),
   BName VARCHAR2(255),
   Address VARCHAR2(255),
   IsOpen VARCHAR2(20),
   City VARCHAR2(255),
   ReviewCount NUMBER,
   State VARCHAR2(255),
   Stars FLOAT,
   Longitude FLOAT,
   Latitude FLOAT,
Attributes VARCHAR2(1024),
neighborhoods VARCHAR2(1024),
   PRIMARY KEY (BId)
);

CREATE TABLE BusinessCategory
(
   CatName VARCHAR2(255),
   BId VARCHAR2(50),
   PRIMARY KEY (BId, CatName),
   FOREIGN KEY (BId) REFERENCES Business (BId) ON DELETE CASCADE
);

CREATE TABLE BusinessSubCategory
(   
   SubCatName VARCHAR2(255),
   BId VARCHAR2(50),
   PRIMARY KEY (BId,SubCatName),
   FOREIGN KEY (BId) REFERENCES Business (BId) ON DELETE CASCADE   
);


CREATE TABLE Checkin(
BId VARCHAR2(50),
CheckDay VARCHAR2(50),
CheckHour NUMBER,
CheckCount NUMBER,
PRIMARY KEY(BId, CheckDay,CheckHour,CheckCount),
FOREIGN KEY(BId) REFERENCES Business (BId) ON DELETE CASCADE
);


CREATE TABLE Review
(   ReviewId VARCHAR2(50),
   UserId VARCHAR2(50),
   FunnyVote number,
   UsefulVote number,
   CoolVote number,
   Stars number,
   ReviewDate date,
   ReviewText clob,
   ReviewType VARCHAR2(255),
   BId VARCHAR2(50),
type VARCHAR2(20),
   primary key (ReviewId),
   FOREIGN KEY (BId) REFERENCES Business (BId) ON DELETE CASCADE,
   FOREIGN  KEY (UserId) REFERENCES yelpuser (UserId) ON DELETE CASCADE
);

create index reviewdate on Review(ReviewDate);
create index reviewBid on Review(BId);
create index reviewuser on Review(UserId);
create index yelpsinceidx on yelpuser(YelpingSince);
create index fidx on Friends(UserId);