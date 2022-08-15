const pages = [
    "Операции", "Читатели", "Книги", "Авторы", "Издательства", "Задолжности"
];

const Sidebar = () => {
    return (
      <div id="Sidebar">
          {pages.map((page) =>
              <div key={page} className="SideItem">{page}</div>
          )}
      </div>
    );
};

export default Sidebar;