from django.conf.urls import include, patterns, url
from django.contrib import admin

urlpatterns = patterns('',
    # Examples:
    url(r'^$', 'lists.views.home_page', name='home'),
    url(
        r'^lists/the-only-list-in-the-world/$',
        'lists.views.view_list',
        name='view_lists'
    )
    # url(r'^blog/', include('blog.urls')),

    # url(r'^admin/', include(admin.site.urls)),
)
