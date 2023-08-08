const useScroll = () => {
  const scrollToTop = () => {
    const mainElement = document.getElementById('main');

    if (mainElement) {
      mainElement.scrollTo(0, 0);
    }
  };

  const scrollToPosition = () => {};

  return { scrollToTop, scrollToPosition };
};

export default useScroll;
