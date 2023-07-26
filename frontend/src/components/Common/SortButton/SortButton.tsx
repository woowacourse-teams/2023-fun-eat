import { Button, Text, useTheme } from '@fun-eat/design-system';
import styled from 'styled-components';

import SvgIcon from '../Svg/SvgIcon';

interface SortButtonProps {
  option: string;
  onClick: () => void;
}

const SortButton = ({ option, onClick }: SortButtonProps) => {
  const theme = useTheme();

  return (
    <SortButtonContainer color="white" variant="filled" onClick={onClick}>
      <SvgIcon variant="sort" color={theme.textColors.info} width={18} height={18} />
      <Text as="span" weight="bold" color={theme.textColors.info}>
        {option}
      </Text>
    </SortButtonContainer>
  );
};

export default SortButton;

const SortButtonContainer = styled(Button)`
  display: flex;
  align-items: center;
  column-gap: 4px;
  padding: 0;
`;
