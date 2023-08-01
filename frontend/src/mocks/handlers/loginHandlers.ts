import { rest } from 'msw';

export const loginHandlers = [
  rest.post('/api/login/oauth2/code/:provider', (req, res, ctx) => {
    const code = req.url.searchParams.get('code');

    if (code !== 'abc') {
      return res(ctx.status(400));
    }

    return res(ctx.status(200), ctx.cookie('mockSessionId', 'abc-123'));
  }),
];
