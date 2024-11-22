const PageComponent = ({ serverData, movePage, cursorStyle, setCursorStyle, searchQuery }) => {
  return (
    <div className="page-buttons">
      {/* Prev Button */}
      {serverData.prev && (
        <div
          onClick={() => movePage({ page: serverData.prevPage, searchQuery })}
          className="p-2 w-16 text-center font-bold text-blue-400 cursor-pointer"
          onMouseEnter={() => setCursorStyle('pointer')}
          onMouseLeave={() => setCursorStyle('default')}
          style={{ cursor: cursorStyle }}
        >
          Prev
        </div>
      )}

      <div className={`p-2 w-12 text-center rounded text-blue cursor-pointer`}>
        {/* Page Number Buttons */}
        {serverData.pageNumList.map((pageNum) => (
          <i
            key={pageNum}
            className={`p-2 w-12 ms-3 ${serverData.current === pageNum ? 'ms-3' : 'ms-3'}`}
            onClick={() => movePage({ page: pageNum, searchQuery })}
            onMouseEnter={() => setCursorStyle('pointer')}
            onMouseLeave={() => setCursorStyle('default')}
            style={{ cursor: cursorStyle }}
          >
            {pageNum}
          </i>
        ))}
      </div>

      {/* Next Button */}
      {serverData.next && (
        <div
          onClick={() => movePage({ page: serverData.nextPage, searchQuery })}
          className="p-2 w-16 text-center font-bold text-blue-400 cursor-pointer"
          onMouseEnter={() => setCursorStyle('pointer')}
          onMouseLeave={() => setCursorStyle('default')}
          style={{ cursor: cursorStyle }}
        >
          Next
        </div>
      )}
    </div>
  );
};

export default PageComponent;
