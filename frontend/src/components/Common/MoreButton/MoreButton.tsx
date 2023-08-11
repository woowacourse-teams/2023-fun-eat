import { Link, Text } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import SvgIcon from '../Svg/SvgIcon';

import { PATH } from '@/constants/path';

const MoreButton = () => {
  return (
    <Link as={RouterLink} to={`${PATH.PRODUCT_LIST}/store`}>
      <MoreButtonWrapper>
        <PlusIconWrapper>
          <SvgIcon variant="plus" color="black" />
        </PlusIconWrapper>
        <Text size="sm" weight="bold">
          더보기
        </Text>
      </MoreButtonWrapper>
    </Link>
  );
};

export default MoreButton;

const MoreButtonWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 110px;
  height: 110px;
  border-radius: 5px;
  background: ${({ theme }) => theme.colors.gray1};
`;

const PlusIconWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  margin-bottom: 5px;
  border-radius: 50%;
  background: ${({ theme }) => theme.colors.white};
`;
