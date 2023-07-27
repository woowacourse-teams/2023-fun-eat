import { Button, Spacing, Text } from '@fun-eat/design-system';
import styled from 'styled-components';

import Logo from '@/assets/logo.svg';
import { SvgIcon } from '@/components/Common';

const DESCRIPTION = '편의점 음식을 편리하게 찾아보고\n꿀조합 레시피를 공유해보세요.';

const LoginPage = () => {
  return (
    <LoginPageContainer>
      <Button variant="transparent">
        <SvgIcon variant="arrow" width={20} height={20} />
      </Button>
      <LoginSection>
        <Logo width={180} />
        <Spacing size={8} />
        <Text size="lg" lineHeight="xl" weight="bold">
          궁금해? 맛있을걸? 먹어봐 🥄
        </Text>
        <Description>{DESCRIPTION}</Description>
        <Spacing size={48} />
        <Button customWidth="100%">카카오 로그인</Button>
      </LoginSection>
    </LoginPageContainer>
  );
};

export default LoginPage;

const LoginPageContainer = styled.section`
  position: absolute;
  top: 0;
  left: 50%;
  width: 100%;
  max-width: 600px;
  height: 100%;
  padding: 20px 20px 0;
  background: ${({ theme }) => theme.backgroundColors.default};
  transform: translateX(-50%);
`;

const LoginSection = styled.section`
  position: absolute;
  top: 50%;
  left: 50%;
  width: 100%;
  padding: 0 20px;
  transform: translate(-50%, -50%);
`;

const Description = styled(Text)`
  white-space: pre-wrap;
`;
