# Records Lab rules

1. **Personal data stays in the HR view.** Pay and national ids are shown only by `/api/hr/**`, and only to a
   request with the header `X-Role: hr`. Every other view (the JSON API, the XML and CSV exports, GraphQL) leaves
   them out.
2. **Money is exact.** Amounts are `BigDecimal`, never `double`, with two decimals, rounded half-even (banker's
   rounding).
3. **Times are UTC.** Every format shows a time as ISO-8601 in UTC to the second, such as `2024-05-01T09:30:00Z`.
4. **Pages are numbered from 1, and hold at most 100.** A missing or smaller page is page 1; a missing size is 20;
   a larger size is cut to 100. This holds in every API, GraphQL included.
5. **Deleted records are gone.** A row with `deleted_at` set is never shown, counted or exported.
6. **The formats agree.** JSON, XML, CSV and GraphQL give the same records with the same values.
7. **Filters are parameters.** Text from a request reaches SQL only as a bound parameter.
8. **CSV cannot run.** A CSV cell that starts with `=`, `+`, `-` or `@` gets a leading `'`, so a spreadsheet shows
   it as text rather than running it as a formula.
9. **One order everywhere.** Records are sorted by family name, then given name, ignoring case, then id, so pages
   never overlap or skip anyone.
10. **Emails are lower case.** In every format, whatever case was stored.
11. **An export is one snapshot.** A full export reads the employees and departments in one read-only transaction,
    so it never mixes data from before and after a change.
12. **Head counts are of active people.** A department's head count leaves out deleted employees.
