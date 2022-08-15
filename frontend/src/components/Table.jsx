const Table = ( { headers, data}) => {
  return (
      <table>
          <tbody>
          <tr>
              {
                  headers.map((row) => <th>{row}</th>)
              }
          </tr>
          {data.map((row) =>
              <tr>
                  {row.map((val, ind) =>
                      <td>{val}</td>
                  )}
              </tr>
          )}
          </tbody>
      </table>
  )
};

export default Table;