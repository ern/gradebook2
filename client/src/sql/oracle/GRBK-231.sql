

create unique index GB_USER_CONFIG_T on GB_USER_DEREFERENCE_T(USER_UID, GRADEBOOK_ID, CONFIG_FIELD);

commit;

