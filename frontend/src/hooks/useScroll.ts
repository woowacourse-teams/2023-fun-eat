const useScroll = () => {
  const scrollToTop = () => {
    const mainElement = document.getElementById('main');

    if (mainElement) {
      mainElement.scrollTo(0, 0);
    }
  };

  const scrollToPosition = (targetRef?: React.RefObject<HTMLElement>) => {
    targetRef?.current?.scrollIntoView({ behavior: 'smooth' });
  };

  return { scrollToTop, scrollToPosition };
};

export default useScroll;
