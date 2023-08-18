import { useTheme } from 'styled-components';

export const convertTagColor = (variant?: string): string => {
  const theme = useTheme();

  switch (variant) {
    case 'TASTE':
      return theme.colors.tertiary;
    case 'QUANTITY':
      return theme.colors.secondary;
    case 'ETC':
      return theme.colors.primary;
    default:
      return theme.colors.primary;
  }
};
