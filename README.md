# Documentation
- `https://super-csv.github.io/super-csv/csv_specification.html`
- `https://super-csv.github.io/super-csv/cell_processors.html`

# Cell processors
Cell processors are an integral part of reading and writing with Super CSV - they automate the data type conversions, and enforce constraints. They implement the chain of responsibility design pattern - each processor has a single, well-defined purpose and can be chained together with other processors to fully automate all of the required conversions and constraint validation for a single CSV column.