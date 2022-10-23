package persei9.accountManagerMs.repositories.queryBuilders

import persei9.accountManagerMs.models.Account.Companion.TABLE_NAME

object AccountQueryBuilder {
    const val INSERT = """
        INSERT INTO $TABLE_NAME
            (
                id, hex_id, serial, email, name,
                avatar, background, phone_numbers, title, address,
                birthday, notes, tags, state, company_id,
                created_by, created_at
            )
        VALUES
            (
                ?::UUID, ?, ?, ?, ?,
                ?, ?, CAST(? AS json), ?, ?,
                ?::DATE, ?, CAST(? AS json), ?, ?::UUID, 
                ?::UUID, ?
            );
    """

    const val FIND_BY_ID = """
        SELECT  id, hex_id, serial, email, name,
                avatar, background, phone_numbers::text, title, address,
                birthday, notes, tags::text, state, company_id,
                created_by, created_at, updated_by, updated_at, deleted_by, deleted_at
        FROM    $TABLE_NAME
        WHERE   id::UUID = ? AND company_id::UUID = ? AND deleted_at IS NULL;
    """

    const val FIND_BY_HEX_ID = """
        SELECT  id, hex_id, serial, email, name,
                avatar, background, phone_numbers::text, title, address,
                birthday, notes, tags::text, state, company_id,
                created_by, created_at, updated_by, updated_at, deleted_by, deleted_at
        FROM    $TABLE_NAME
        WHERE   hex_id = ? AND company_id::UUID = ? AND deleted_at IS NULL;
    """

    const val LIST_ALL = """
        SELECT  id, hex_id, serial, email, name,
                avatar, background, phone_numbers::text, title, address,
                birthday, notes, tags::text, state, company_id,
                created_by, created_at, updated_by, updated_at, deleted_by, deleted_at
        FROM    $TABLE_NAME
        WHERE   company_id = ? AND deleted_at IS NULL;
    """
}