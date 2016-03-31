'use strict';

angular.module('consultorioOnlineAppApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('patient', {
                parent: 'entity',
                url: '/patients',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineAppApp.patient.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/patient/patients.html',
                        controller: 'PatientController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('patient');
                        $translatePartialLoader.addPart('bloodType');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('patient.detail', {
                parent: 'entity',
                url: '/patient/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineAppApp.patient.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/patient/patient-detail.html',
                        controller: 'PatientDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('patient');
                        $translatePartialLoader.addPart('bloodType');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('gender');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Patient', function($stateParams, Patient) {
                        return Patient.get({id : $stateParams.id});
                    }]
                }
            })
            .state('patient.new', {
                parent: 'patient',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/patient/patient-dialog.html',
                        controller: 'PatientDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    firstName: null,
                                    middleName: null,
                                    lastName: null,
                                    dateOfBirth: null,
                                    job: null,
                                    bloodType: null,
                                    maritalStatus: null,
                                    gender: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('patient', null, { reload: true });
                    }, function() {
                        $state.go('patient');
                    })
                }]
            })
            .state('patient.edit', {
                parent: 'patient',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/patient/patient-dialog.html',
                        controller: 'PatientDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Patient', function(Patient) {
                                return Patient.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('patient', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('patient.delete', {
                parent: 'patient',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/patient/patient-delete-dialog.html',
                        controller: 'PatientDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Patient', function(Patient) {
                                return Patient.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('patient', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
