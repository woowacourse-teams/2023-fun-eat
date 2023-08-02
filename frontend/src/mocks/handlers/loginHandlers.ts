import { rest } from 'msw';

export const loginHandlers = [
  rest.post('/api/login/oauth2/code/:provider', (req, res, ctx) => {
    const code = req.url.searchParams.get('code');

    if (code === 'abc') {
      return res(
        ctx.status(200),
        ctx.cookie('mockSessionId', 'abc123', { path: '/' }),
        ctx.set('Location', '/profile')
      );
    }

    if (code === 'qwe') {
      return res(ctx.status(200), ctx.cookie('mockSessionId', 'qwe456', { path: '/' }), ctx.set('Location', '/'));
    }

    return res(ctx.status(400));
  }),
];
