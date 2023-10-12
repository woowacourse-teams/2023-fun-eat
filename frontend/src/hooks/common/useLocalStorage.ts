const useLocalStorage = (key: string) => {
  const getLocalStorage = () => {
    const item = localStorage.getItem(key);

    if (item) {
      try {
        return JSON.parse(item);
      } catch (error) {
        return item;
      }
    }

    return null;
  };

  const setLocalStorage = (newValue: unknown) => {
    if (typeof newValue === 'string') {
      localStorage.setItem(key, newValue);
      return;
    }

    localStorage.setItem(key, JSON.stringify(newValue));
  };

  const removeLocalStorage = () => {
    localStorage.removeItem(key);
  };

  return {
    getLocalStorage,
    setLocalStorage,
    removeLocalStorage,
  };
};

export default useLocalStorage;
