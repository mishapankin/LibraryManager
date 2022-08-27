const Table = ( { headers, data}) => {
  return (
      <table>
          <tbody>
          <tr>
              {
                  headers.map((row) => <th key={row}>{row}</th>)
              }
          </tr>
          {data.map((row) =>
              <tr key={row.toString()}>
                  {row.map((val, ind) =>
                      <td key={headers[ind]}>{val}</td>
                  )}
              </tr>
          )}
          </tbody>
      </table>
  )
};

export default Table;