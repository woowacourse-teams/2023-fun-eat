import { Button, Spacing, Text } from '@fun-eat/design-system';
import styled from 'styled-components';

import Logo from '@/assets/logo.svg';
import { SvgIcon } from '@/components/Common';
import { useLogin } from '@/hooks/auth';
import useRouteBack from '@/hooks/useRouteBack';

const DESCRIPTION = '편의점 음식을 편리하게 찾아보고\n꿀조합 레시피를 공유해보세요.';

const LoginPage = () => {
  const routeBack = useRouteBack();
  const { handleLogin } = useLogin();

  return (
    <>
      <Button type="button" variant="transparent" onClick={routeBack}>
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
        <KakaoLoginButton type="button" customWidth="100%" onClick={() => handleLogin('kakao')}>
          <SvgIcon variant="kakao" width={20} height={20} />
          <Text as="span">카카오 로그인</Text>
        </KakaoLoginButton>
      </LoginSection>
    </>
  );
};

export default LoginPage;

const LoginSection = styled.section`
  position: absolute;
  top: 50%;
  left: 50%;
  width: 100%;
  max-width: 600px;
  padding: 0 20px;
  transform: translate(-50%, -50%);
`;

const Description = styled(Text)`
  white-space: pre-wrap;
  word-break: break-all;
`;

const KakaoLoginButton = styled(Button)`
  display: flex;
  align-items: center;
  height: 54px;
  padding: 0 16px;
  background-color: #fee500;
  border-radius: 12px;

  & > span {
    width: 100%;
    color: rgba(0, 0, 0, 0.85);
    text-align: center;
  }
`;
