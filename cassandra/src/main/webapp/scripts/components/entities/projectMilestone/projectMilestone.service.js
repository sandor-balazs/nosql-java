'use strict';

angular.module('ontimeApp')
    .factory('ProjectMilestone', function ($resource, DateUtils) {
        return $resource('api/projectMilestones/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.planned = DateUtils.convertDateTimeFromServer(data.planned);
                    data.actual = DateUtils.convertDateTimeFromServer(data.actual);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
